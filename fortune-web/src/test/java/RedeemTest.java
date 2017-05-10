import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.FpayUtil;
import lombok.extern.log4j.Log4j2;

import java.io.OutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Log4j2
public class RedeemTest {

    /**
     * 商户号
     */
    private static String merchCo = "201705050000001";

    /**
     * 交易码
     */
    private static String tranCo = "K004";

    /**
     * 字符集
     */
    private static String charset = "UTF-8";

    /**
     * 调试模式
     */
    private static boolean isDebug = false;

//    /**
//     * 对方公钥路径
//     */
//    private static String publicKeyPath = "E:/data/fpay/fpay/fpay_rsa_public_key_2048.pem";
//
//    /**
//     * 己方私钥路径
//     */
//    private static String privateKeyPath = "E:/data/fpay/merch/merch_pkcs8_rsa_private_key_2048.pem";
    /**
     * 对方公钥路径
     */
    private static String publicKeyPath = "/Users/kyg/data/fpay/fpay/fpay_rsa_public_key_2048.pem";

    /**
     * 己方私钥路径
     */
    private static String privateKeyPath = "/Users/kyg/data/fpay/merch/merch_pkcs8_rsa_private_key_2048.pem";


    public static void main(String[] args) throws Exception {
        // 公钥
        PublicKey publicKey = FpayUtil.getPublicKey(publicKeyPath, isDebug);
        // 私钥
        PrivateKey privateKey = FpayUtil.getPrivateKey(privateKeyPath, isDebug);
        log.info("加载公钥私钥完成");

        // 报文
        String plain = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<fpay> \n" +
                "    <serialNo>" + DateUtil.getFullDateTime() + "</serialNo>  \n" +
                "    <reqDate>20170507</reqDate>  \n" +
                "    <reqTime>153623</reqTime>  \n" +
                "    <protocolNo>201705100000000000000000000000000000000000000001</protocolNo>  \n" +
                "    <amount>2.00</amount>  \n" +
                "</fpay>";
        log.info("请求报文明文:{}", plain);

        // 签名
        byte[] signBytes = FpayUtil.sign(plain, privateKey, charset, isDebug);
        log.info("请求报文签名数据长度:{}", signBytes.length);

        // 加密
        byte[] encryptedBytes = FpayUtil.encrypt(plain, publicKey, charset, isDebug);
        log.info("请求报文密文长度{}", encryptedBytes.length);

        byte bytes[] = FpayUtil.build(merchCo, tranCo, signBytes, encryptedBytes);

        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream out = socket.getOutputStream();

        out.write(bytes);

        out.flush();
        log.info("请求完毕");

        Map<String, Object> resMap = FpayUtil.parse(socket.getInputStream());
        // 解密
        String reqXml;
        try {
            reqXml = FpayUtil.decrypt((byte[]) resMap.get("encryptedBytes"), privateKey, charset, isDebug);
            log.info("报文解密后:\n{}", reqXml);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return;
        }
    }

}
