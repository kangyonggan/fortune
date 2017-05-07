package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.FpayUtil;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.common.util.XmlUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.Resp;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.thoughtworks.xstream.XStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
@Log4j2
public class FpayHelper {

    /**
     * redis键的前缀
     */
    private String prefix = PropertiesUtil.getProperties("redis.prefix") + ":";

    @Autowired
    private RedisService redisService;

    /**
     * 写响应
     *
     * @param out        输出流
     * @param publicKey  对方公钥
     * @param privateKey 己方私钥
     * @param merchCo    商户号
     * @param tranCo     交易码
     * @param resp       响应码
     * @param fpay       请求/响应
     */
    public static void writeResponse(OutputStream out, PublicKey publicKey, PrivateKey privateKey, String merchCo, String tranCo, Resp resp, Fpay fpay) {
        try {
            String respXml = buildRespXml(resp, fpay);
            writeResponse(out, publicKey, privateKey, merchCo, tranCo, respXml);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 实际写响应
     *
     * @param out        输出流
     * @param publicKey  对方公钥
     * @param privateKey 己方私钥
     * @param merchCo    商户号
     * @param tranCo     交易码
     * @param respXml    响应报文
     */
    private static void writeResponse(OutputStream out, PublicKey publicKey, PrivateKey privateKey, String merchCo, String tranCo, String respXml) {
        try {
            respXml = XmlUtil.format(respXml);
            log.info("响应报文明文:\n{}", respXml);
            // 签名
            byte[] signBytes = FpayUtil.sign(respXml, privateKey);
            log.info("响应报文签名数据长度:{}", signBytes.length);

            // 加密
            byte[] encryptedBytes = FpayUtil.encrypt(respXml, publicKey);
            log.info("响应报文密文长度{}", encryptedBytes.length);

            // 构建报文
            byte bytes[] = FpayUtil.build(merchCo, tranCo, signBytes, encryptedBytes);

            out.write(bytes);
            out.flush();
            log.info("响应回写完毕");
        } catch (Exception e) {
            log.warn("回写响应报文异常", e);
        }
    }

    /**
     * 构建响应报文
     *
     * @param resp 响应码
     * @param resp 请求
     * @return
     * @throws Exception
     */
    private static String buildRespXml(Resp resp, Fpay fpay) throws Exception {
        XStream xStream = XStreamUtil.getXStream();
        xStream.processAnnotations(Fpay.class);

        if (fpay == null) {
            fpay = new Fpay();
        }
        if (resp != null) {
            fpay.setRespCo(resp.getRespCo());
            fpay.setRespMsg(resp.getRespMsg());
        }

        return xStream.toXML(fpay);
    }

    /**
     * 生成流水号
     *
     * @return
     */
    public String genSerialNo() {
        String nextVal = String.valueOf(redisService.incr(prefix + AppConstants.COMMAND_SERIAL_NO));
        String currentDate = DateUtil.getDate();

        return currentDate + StringUtils.leftPad(nextVal, 12, "0");
    }

    /**
     * 生成协议号
     *
     * @return
     */
    public String genProtocolNo() {
        String nextVal = String.valueOf(redisService.incr(prefix + AppConstants.SIGN_PROTOCOL_NO));
        String currentDate = DateUtil.getDate();

        return currentDate + StringUtils.leftPad(nextVal, 40, "0");
    }
}
