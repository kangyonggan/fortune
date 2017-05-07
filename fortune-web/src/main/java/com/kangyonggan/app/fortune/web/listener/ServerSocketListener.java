package com.kangyonggan.app.fortune.web.listener;

import com.kangyonggan.app.fortune.biz.service.FpayHelper;
import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.TransService;
import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.biz.util.SpringContextHolder;
import com.kangyonggan.app.fortune.common.exception.EmptyParamsException;
import com.kangyonggan.app.fortune.common.exception.ValidParamsException;
import com.kangyonggan.app.fortune.common.util.FpayUtil;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.Resp;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Trans;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.thoughtworks.xstream.XStream;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Log4j2
public class ServerSocketListener implements ServletContextListener {

    private ServerSocket serverSocket;
    private boolean isRun = true;

    private MerchantService merchantService;

    private FpayService fpayService;

    private TransService transService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        stop();
    }

    /**
     * 启动服务
     */
    public void start() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(8888);
                log.info("服务已正常启动，正在监听...");
            } catch (IOException e) {
                log.warn("服务启动异常:{}", e.getMessage());
                stop();
            }

            while (isRun) {
                execute();
            }
        }).start();
    }

    /**
     * 核心执行器
     */
    private void execute() {
        // 等待客户端连接
        Socket socket;
        try {
            socket = serverSocket.accept();
            log.info("新的客户端连接...");
        } catch (Exception e) {
            log.warn("客户端连接异常:{}", e.getMessage());
            return;
        }

        // 读取请求信息
        Map<String, Object> requestMap;
        OutputStream out;
        try {
            out = socket.getOutputStream();
            requestMap = FpayUtil.parse(socket.getInputStream());
        } catch (Exception e) {
            log.warn(e.getMessage());
            close(socket);
            return;
        }

        String merchCo = (String) requestMap.get("merchCo");
        String tranCo = (String) requestMap.get("tranCo");

        // 商户信息
        Merchant merchant;
        try {
            if (merchantService == null) {
                merchantService = SpringContextHolder.getBean(MerchantService.class);
            }
            merchant = merchantService.findMerchantByMerchCo(merchCo);
        } catch (Exception e) {
            log.warn("查找商户信息异常:{}", e.getMessage());
            close(socket);
            return;
        }
        if (merchant == null) {
            log.warn("商户信息不存在");
            close(socket);
            return;
        }

        // 是否是调试模式
        boolean isDebug = merchant.getIsDebug() == 1;
        // 字符集
        String charset = merchant.getCharset();

        // 公钥
        PublicKey publicKey;
        // 私钥
        PrivateKey privateKey;
        try {
            publicKey = FpayUtil.getPublicKey(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + merchant.getPublicKeyPath(), isDebug);
            privateKey = FpayUtil.getPrivateKey(merchant.getPrivateKeyPath(), isDebug);
            log.info("加载公钥使用完成");
        } catch (Exception e) {
            log.warn(e.getMessage());
            close(socket);
            return;
        }

        // 解密
        String reqXml;
        try {
            reqXml = FpayUtil.decrypt((byte[]) requestMap.get("encryptedBytes"), privateKey, charset, isDebug);
            log.info("报文解密后:{}", reqXml);
        } catch (Exception e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0002, null);
            return;
        }

        // 解析
        Fpay fpay;
        try {
            XStream xStream = XStreamUtil.getXStream();
            xStream.processAnnotations(Fpay.class);
            fpay = (Fpay) xStream.fromXML(reqXml);
        } catch (Exception e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0003, null);
            return;
        }

        // 验签
        boolean isValid;
        try {
            isValid = FpayUtil.isValid(reqXml, (byte[]) requestMap.get("signBytes"), publicKey, charset, isDebug);
            log.info("报文验签结果:{}", isValid);
        } catch (Exception e) {
            log.warn("验签异常:{}", e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0004, fpay);
            return;
        }
        if (!isValid) {
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0005, fpay);
            return;
        }

        if (fpayService == null) {
            fpayService = SpringContextHolder.getBean(FpayService.class);
        }

        // 商户是否支持此交易
        if (transService == null) {
            transService = SpringContextHolder.getBean(TransService.class);
        }
        try {
            Trans trans = transService.findTransByMerchCoAndTranCo(merchCo, tranCo);
            if (trans == null || trans.getIsPaused() == 1) {
                FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0009, fpay);
                return;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_9999, fpay);
            return;
        }

        // 分发请求
        try {
            if (TranCo.K001.name().equals(tranCo)) {
                fpayService.sign(merchCo, fpay);
            } else if (TranCo.K002.name().equals(tranCo)) {
                fpayService.unsign(merchCo, fpay);
            } else if (TranCo.K003.name().equals(tranCo)) {
                fpayService.pay(merchCo, fpay);
            } else if (TranCo.K004.name().equals(tranCo)) {
                fpayService.redeem(merchCo, fpay);
            } else if (TranCo.K005.name().equals(tranCo)) {
                fpayService.query(merchCo, fpay);
            } else if (TranCo.K006.name().equals(tranCo)) {
                fpayService.queryBalance(merchCo, fpay);
            } else {
                FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0006, fpay);
                return;
            }
        } catch (EmptyParamsException e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0007, fpay);
            return;
        } catch (ValidParamsException e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_0008, fpay);
            return;
        } catch (Exception e) {
            log.warn(e.getMessage());
            FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, Resp.RESP_CO_9999, fpay);
            return;
        }

        // 回写正常响应
        FpayHelper.writeResponse(out, publicKey, privateKey, charset, isDebug, merchCo, tranCo, null, fpay);
    }

    /**
     * 停止服务
     */
    public void stop() {
        isRun = false;

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log.warn("关闭服务套接字异常:{}", e.getMessage());
            }
        }
        log.info("服务已停止");
    }

    /**
     * 关闭连接
     *
     * @param socket
     */
    private void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
                log.info("已主动关闭链接");
            } catch (Exception e) {
                log.warn("关闭链接异常", e);
            }
        }
    }

}
