package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.common.util.XmlUtil;
import com.kangyonggan.app.fortune.model.vo.Resp;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class FpayHelper {

    @Autowired
    private RespService respService;

    /**
     * 构建异常报文
     *
     * @param reqFpay
     * @param respCode
     * @return
     * @throws Exception
     */
    public String buildErrorXml(Fpay reqFpay, String respCode) throws Exception {
        // 响应码
        Resp resp = respService.findRespByRespCo(respCode);

        XStream xStream = XStreamUtil.getXStream();
        xStream.processAnnotations(Fpay.class);

        // 响应头
        Header header = new Header();
        if (reqFpay != null) {
            header.setMerchCo(reqFpay.getHeader().getMerchCo());
            header.setTranCo(reqFpay.getHeader().getTranCo());
            header.setSerialNo(reqFpay.getHeader().getSerialNo());
        } else {
            header.setMerchCo("");
            header.setTranCo("");
            header.setSerialNo("");
        }
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());

        // 响应报文整体
        Fpay respFpay = new Fpay();
        respFpay.setHeader(header);

        // 转xml
        String respXml = xStream.toXML(respFpay);

        // 格式化xml
        respXml = XmlUtil.format(respXml);
        return respXml;
    }

    /**
     * 入参校验
     *
     * @param fpay
     * @return
     */
    public Map<String, Object> validParams(Fpay fpay) {
        Map<String, Object> result = new HashMap();
        result.put("isValid", true);
        // TODO 校验各个参数，校验失败塞respCo

        return result;
    }
}
