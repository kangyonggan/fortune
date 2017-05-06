package com.kangyonggan.app.fortune.web.listener;

import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.biz.util.SpringContextHolder;
import com.kangyonggan.app.fortune.common.util.FpayUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.RespCo;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                // 等待客户端连接
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    log.info("新的客户端连接...");
                } catch (Exception e) {
                    log.warn("客户端连接异常:{}", e.getMessage());
                    continue;
                }

                // 读取请求信息
                Map<String, Object> requestMap;
                OutputStream out;
                try {
                    out = socket.getOutputStream();
                    requestMap = FpayUtil.parse(socket.getInputStream());
                } catch (Exception e) {
                    log.warn(e.getMessage());
                    continue;
                }

                // 商户信息
                Merchant merchant;
                try {
                    if (merchantService == null) {
                        merchantService = SpringContextHolder.getBean(MerchantService.class);
                    }
                    merchant = merchantService.findMerchantByMerchCo((String) requestMap.get("merchCo"));
                } catch (Exception e) {
                    log.warn("查找商户信息异常:{}", e.getMessage());
                    continue;
                }
                if (merchant == null) {
                    log.warn("商户信息不存在");
                    continue;
                }

                // 公钥
                PublicKey publicKey;
                // 私钥
                PrivateKey privateKey;
                try {
                    publicKey = FpayUtil.getPublicKey(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + merchant.getPublicKeyPath());
                    privateKey = FpayUtil.getPrivateKey(merchant.getPrivateKeyPath());
                } catch (Exception e) {
                    log.warn(e.getMessage());
                    continue;
                }

                // 解密
                String reqXml;
                try {
                    reqXml = FpayUtil.decrypt((byte[]) requestMap.get("encryptedBytes"), privateKey);
                    log.info("报文解密后:{}", reqXml);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                    writeResponse(out, requestMap, publicKey, privateKey, RespCo.RESP_CO_0008);
                    continue;
                }

                // 验签
                boolean isValid;
                try {
                    isValid = FpayUtil.isValid(reqXml, (byte[]) requestMap.get("signBytes"), publicKey);
                    log.info("报文验签结果:{}", isValid);
                } catch (Exception e) {
                    log.warn("验签异常:{}", e.getMessage());
                    writeResponse(out, requestMap, publicKey, privateKey, RespCo.RESP_CO_0009);
                    continue;
                }
                if (!isValid) {
                    writeResponse(out, requestMap, publicKey, privateKey, RespCo.RESP_CO_0009);
                    continue;
                }

                // 分发请求
                String respXml;
                String tranCo = (String) requestMap.get("tranCo");
                if (TranCo.K001.name().equals(tranCo)) {
                    respXml = "";
                } else if (TranCo.K002.name().equals(tranCo)) {
                    respXml = "";
                } else if (TranCo.K003.name().equals(tranCo)) {
                    respXml = "";
                } else if (TranCo.K004.name().equals(tranCo)) {
                    respXml = "";
                } else if (TranCo.K005.name().equals(tranCo)) {
                    respXml = "";
                } else if (TranCo.K006.name().equals(tranCo)) {
                    respXml = "";
                } else {
                    respXml = "";
                }

                // 回写响应
                writeResponse(out, requestMap, publicKey, privateKey, respXml);
            }
        }).start();
    }

    /**
     * 写响应
     *
     * @param out        输出流
     * @param requestMap 请求数据
     * @param publicKey  对方公钥
     * @param privateKey 己方私钥
     * @param resp       响应码
     */
    private void writeResponse(OutputStream out, Map<String, Object> requestMap, PublicKey publicKey, PrivateKey privateKey, RespCo resp) {
        try {
            String respXml = buildErrorRespXml(resp, requestMap);
            writeResponse(out, requestMap, publicKey, privateKey, respXml);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 写响应
     *
     * @param out        输出流
     * @param requestMap 请求数据
     * @param publicKey  对方公钥
     * @param privateKey 己方私钥
     * @param respXml    响应报文
     */
    private void writeResponse(OutputStream out, Map<String, Object> requestMap, PublicKey publicKey, PrivateKey privateKey, String respXml) {
        try {
            log.info("响应报文明文:{}", respXml);
            // 签名
            byte[] signBytes = FpayUtil.sign(respXml, privateKey);
            log.info("响应报文签名数据长度:{}", signBytes.length);

            // 加密
            byte[] encryptedBytes = FpayUtil.encrypt(respXml, publicKey);
            log.info("响应报文密文长度{}", encryptedBytes.length);

            // 构建报文
            byte bytes[] = FpayUtil.build((String) requestMap.get("merchCo"), (String) requestMap.get("tranCo"), signBytes, encryptedBytes);

            out.write(bytes);
            out.flush();
            log.info("响应回写完毕");
        } catch (Exception e) {
            log.warn("回写响应报文异常", e);
        }
    }

    /**
     * 构建异常响应报文异常
     *
     * @param resp
     * @param requestMap
     * @return
     */
    public String buildErrorRespXml(RespCo resp, Map<String, Object> requestMap) {
        try {

        } catch (Exception e) {

        }

        return "";
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

}
