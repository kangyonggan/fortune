import com.kangyonggan.app.fortune.common.exception.ParseException;
import com.kangyonggan.app.fortune.common.util.FpayUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Log4j2
public class SocketTest {

    /**
     * 商户号
     */
    private static String merchCo = "201705050000001";

    /**
     * 对方公钥路径
     */
    private static String publicKeyPath = "E:/data/fpay/fpay/fpay_rsa_public_key_2048.pem";

    /**
     * 己方私钥路径
     */
    private static String privateKeyPath = "E:/data/fpay/merch/merch_pkcs8_rsa_private_key_2048.pem";

    public static void main(String[] args) throws Exception {
        // 公钥
        PublicKey publicKey = FpayUtil.getPublicKey(publicKeyPath);

        // 私钥
        PrivateKey privateKey = FpayUtil.getPrivateKey(privateKeyPath);

        // 报文
        String plain = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        log.info("请求报文明文:{}", plain);

        // 签名
        byte[] signBytes = FpayUtil.sign(plain, privateKey);
        log.info("请求报文签名数据长度:{}", signBytes.length);

        // 加密
        byte[] encryBytes = FpayUtil.encrypt(plain, publicKey);
        log.info("请求报文密文长度{}", encryBytes.length);

        // 报文头
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.leftPad(String.valueOf(15 + 4 + 4 + signBytes.length + encryBytes.length), 8, "0"));
        sb.append(StringUtils.leftPad(merchCo, 15, " "));
        sb.append(StringUtils.leftPad("K001", 4, " "));
        sb.append(StringUtils.leftPad(String.valueOf(signBytes.length), 4, "0"));
        log.info("请求报文头:{}", sb.toString());

        // 组装报文
        byte[] bytes = null;
        bytes = ArrayUtils.addAll(bytes, sb.toString().getBytes("UTF-8"));
        bytes = ArrayUtils.addAll(bytes, signBytes);
        bytes = ArrayUtils.addAll(bytes, encryBytes);

        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream out = socket.getOutputStream();

        out.write(bytes);

        out.flush();
        log.info("请求完毕");

        // 商户号
        String merchCo;
        // 交易代码
        String tranCo;
        InputStream in = null;
        try {
            in = socket.getInputStream();
            byte buff[] = new byte[9999];

            // 读取报文头结构, 报文总长度8位+商户号15位+交易码4位+签名与长度4位=31位
            in.read(buff, 0, 31);
            int totalLen = Integer.parseInt(new String(buff, 0, 8, AppConstants.CHARSET));
            log.info("报文总长度：{}", totalLen);

            merchCo = new String(buff, 8, 15, AppConstants.CHARSET).trim();
            log.info("商户号：{}", merchCo);

            tranCo = new String(buff, 23, 4, AppConstants.CHARSET).trim();
            log.info("交易码：{}", tranCo);

            int signLen = Integer.parseInt(new String(buff, 27, 4, AppConstants.CHARSET));
            log.info("签名长度：{}", signLen);

            int encryLen = totalLen - 15 - 4 - 4 - signLen;
            log.info("密文长度：{}", encryLen);

            in.read(buff, 0, signLen);
            signBytes = ArrayUtils.subarray(buff, 0, signLen);
            log.info("实际读到的签名长度:{}", signBytes.length);

            in.read(buff, 0, encryLen);
            encryBytes = ArrayUtils.subarray(buff, 0, encryLen);
            log.info("实际读到的密文长度:{}", encryBytes.length);
        } catch (Exception e) {
            throw new ParseException("解析请求报文异常:" + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
