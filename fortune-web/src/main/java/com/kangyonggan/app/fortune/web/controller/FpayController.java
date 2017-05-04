package com.kangyonggan.app.fortune.web.controller;

import com.kangyonggan.app.fortune.biz.service.FpayHelper;
import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.common.exception.BuildException;
import com.kangyonggan.app.fortune.common.exception.ReceiveException;
import com.kangyonggan.app.fortune.common.exception.SendException;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.thoughtworks.xstream.XStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 发财付接口
 *
 * @author kangyonggan
 * @since 5/4/17
 */
@Controller
@RequestMapping("fpay")
@Log4j2
public class FpayController {

    @Autowired
    private FpayService fpayService;

    @Autowired
    private FpayHelper fpayHelper;

    /**
     * 交易总入口
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST)
    public void fpay(HttpServletRequest request, HttpServletResponse response) {
        log.info("==================== 进入发财付平台交易总入口 ====================");
        // 1. 读请求
        String reqXml;
        try {
            reqXml = readReqXml(request);
        } catch (ReceiveException e) {
            processException(response, "0002");
            return;
        } catch (Exception e) {
            processException(response, "0003");
            return;
        }

        // 2. 解析xml
        Fpay fpay;
        try {
            XStream xStream = XStreamUtil.getXStream();
            xStream.processAnnotations(Fpay.class);
            fpay = (Fpay) xStream.fromXML(reqXml);
        } catch (Exception e) {
            processException(response, "0004");
            return;
        }

        // TODO 交易落库, command表的约束不能那么强

        // 3. 入参校验
        try {
            Map<String, Object> validResult = fpayHelper.validParams(fpay);
            boolean isValid = (boolean) validResult.get("isValid");
            log.info("入参校验结果:{}", isValid);

            if (!isValid) {
                log.info("入参校验失败，错误码：{}", validResult.get("respCo"));
                fpayHelper.buildErrorXml(fpay, (String) validResult.get("respCo"));
                return;
            }
        } catch (Exception e) {
            processException(response, "0004");
            return;
        }

        // 4. 分发请求
        String respXml;
        try {
            String tranCo = fpay.getHeader().getTranCo();
            log.info("交易代码：{}", tranCo);
            if (TranCo.K001.name().equals(tranCo)) {
                // 签约
                respXml = fpayService.sign(fpay);
            } else if (TranCo.K002.name().equals(tranCo)) {
                // 解约
                respXml = fpayService.unsign(fpay);
            } else if (TranCo.K003.name().equals(tranCo)) {
                // 单笔代扣
                respXml = fpayService.pay(fpay);
            } else if (TranCo.K004.name().equals(tranCo)) {
                // 单笔代付
                respXml = fpayService.redeem(fpay);
            } else if (TranCo.K005.name().equals(tranCo)) {
                // 交易查询
                respXml = fpayService.query(fpay);
            } else if (TranCo.K006.name().equals(tranCo)) {
                // 余额查询
                respXml = fpayService.queryBalance(fpay);
            } else {
                processException(response, fpay, "0009");
                return;
            }
        } catch (BuildException pe) {
            processException(response, fpay, "0005");
            return;
        } catch (Exception e) {
            processException(response, fpay, "0006");
            return;
        }

        // 5. 写响应
        try {
            wirteRespXml(response, respXml);
        } catch (SendException e) {
            processException(response, fpay, "0007");
            return;
        } catch (Exception e) {
            processException(response, fpay, "0008");
            return;
        }
        log.info("==================== 离开发财付平台交易总入口 ====================");
    }

    /**
     * 写响应报文
     *
     * @param response 响应
     * @param respXml  响应报文
     * @throws SendException 发送报文异常
     * @throws Exception     未知异常
     */
    private void wirteRespXml(HttpServletResponse response, String respXml) throws SendException, Exception {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(respXml.getBytes(AppConstants.CHARSET));
            out.flush();

            log.info("响应报文回写完毕：\n{}", respXml);
        } catch (IOException e) {
            throw new SendException("回写响应报文异常", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new SendException("回写响应报文后，关闭流异常", e);
            }
        }
    }

    /**
     * 读请求报文
     *
     * @param request 请求
     * @throws ReceiveException 接收异常
     * @throws Exception        未知异常
     */
    private String readReqXml(HttpServletRequest request) throws ReceiveException, Exception {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            in = request.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            out.flush();

            String reqXml = new String(out.toByteArray(), AppConstants.CHARSET);
            log.info("商户请求报文: \n{}", reqXml);

            return reqXml;
        } catch (IOException e) {
            throw new ReceiveException("获取商户请求报文异常", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new ReceiveException("获取商户请求报文后，关闭流异常", e);
            }
        }
    }

    /**
     * 处理异常
     *
     * @param response 响应
     * @param reqFpay  请求报文
     * @param respCode 响应码
     */
    private void processException(HttpServletResponse response, Fpay reqFpay, String respCode) {
        OutputStream out = null;
        try {
            String respXml = fpayHelper.buildErrorXml(reqFpay, respCode);

            out = response.getOutputStream();
            out.write(respXml.getBytes(AppConstants.CHARSET));
            out.flush();

            log.info("异常处理程序处理完毕：\n{}", respXml);
        } catch (IOException e) {
            log.error("异常处理程序IO异常", e);
        } catch (Exception e) {
            log.error("异常处理程序未知异常", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("异常处理程序关闭流异常", e);
            }
        }
    }

    /**
     * 处理异常
     *
     * @param response 响应
     * @param respCode 响应码
     */
    private void processException(HttpServletResponse response, String respCode) {
        processException(response, null, respCode);
    }
}


