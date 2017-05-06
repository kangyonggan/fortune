package com.kangyonggan.app.fortune.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 发财付相关工具
 *
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
public class FpayUtil {

    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 生成密钥工厂算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 数字签名算法
     */
    private static final String SIGN_ALGORITHM = "SHA1WithRSA";

    /**
     * 加密算法
     */
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 获取对方公钥
     *
     * @param publicKeyPath 公钥绝对路径
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyPath) throws Exception {
        InputStream in = null;
        try {
            in = new FileInputStream(publicKeyPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String readLine;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(pubX509);
            return publicKey;
        } catch (Exception e) {
            throw new Exception("加载对方公钥失败[" + publicKeyPath + "]", e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 获取己方私钥
     *
     * @param privateKeyPath 私钥绝对路径
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        InputStream in = null;
        try {
            in = new FileInputStream(privateKeyPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String readLine;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);

            return privateKey;
        } catch (Exception e) {
            throw new Exception("加载己方私钥失败[" + privateKeyPath + "]", e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 签名
     *
     * @param plain      明文
     * @param privateKey 己方私钥
     * @return
     * @throws Exception
     */
    public static byte[] sign(String plain, PrivateKey privateKey) throws Exception {
        byte plainBytes[] = plain.getBytes(DEFAULT_CHARSET);
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(plainBytes);
        byte[] signBytes = signature.sign();
        return signBytes;
    }

    /**
     * 验签
     *
     * @param plain     明文
     * @param signBytes 签名数据
     * @param publicKey 对方公钥
     * @return
     * @throws Exception
     */
    public static boolean isValid(String plain, byte[] signBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(plain.getBytes(DEFAULT_CHARSET));
        return signature.verify(signBytes);
    }

    /**
     * 加密
     *
     * @param plain     明文
     * @param publicKey 对方公钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plain, PublicKey publicKey) throws Exception {
        byte plainBytes[] = plain.getBytes(DEFAULT_CHARSET);
        int keyByteSize = 2048 / 8;
        int encryptBlockSize = keyByteSize - 11;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }

        ByteArrayOutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            out = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                out.write(encryptedBlock);
            }

            out.flush();
            return out.toByteArray();
        } catch (Exception e) {
            throw new Exception("加密失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 解密
     *
     * @param encryptedBytes 密文
     * @param privateKey     己方私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] encryptedBytes, PrivateKey privateKey) throws Exception {
        int keyByteSize = 2048 / 8;
        int decryptBlockSize = keyByteSize - 11;
        int nBlock = encryptedBytes.length / keyByteSize;

        ByteArrayOutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            out = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                out.write(decryptedBlock);
            }

            out.flush();
            return new String(out.toByteArray(), DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new Exception("解密失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 构建报文
     *
     * @param merchCo        商户号
     * @param tranCo         交易码
     * @param signBytes      签名数据
     * @param encryptedBytes 加密数据
     * @return
     * @throws Exception
     */
    public static byte[] build(String merchCo, String tranCo, byte signBytes[], byte encryptedBytes[]) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.leftPad(String.valueOf(15 + 4 + 4 + signBytes.length + encryptedBytes.length), 8, "0"));// 域1 总长度
        sb.append(StringUtils.leftPad(merchCo, 15, " "));// 域2 商户号
        sb.append(StringUtils.leftPad(tranCo, 4, " "));// 域3 交易码
        sb.append(StringUtils.leftPad(String.valueOf(signBytes.length), 4, "0"));// 域4 签名长度

        byte[] bytes = null;
        bytes = ArrayUtils.addAll(bytes, sb.toString().getBytes("UTF-8"));// 前4域的组合
        bytes = ArrayUtils.addAll(bytes, signBytes);// 域5 签名数据
        return ArrayUtils.addAll(bytes, encryptedBytes);// 域6 加密数据
    }

    /**
     * 解析报文
     *
     * @param in 输入流
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parse(InputStream in) throws Exception {
        Map<String, Object> map = new HashMap();
        // 商户号
        String merchCo;
        // 交易代码
        String tranCo;
        // 签名
        byte signBytes[];
        // 密文
        byte encryptedBytes[];

        try {
            byte buff[] = new byte[9999];

            // 读取报文头结构, 报文总长度8位+商户号15位+交易码4位+签名与长度4位=31位
            in.read(buff, 0, 31);
            int totalLen = Integer.parseInt(new String(buff, 0, 8, DEFAULT_CHARSET));
            merchCo = new String(buff, 8, 15, DEFAULT_CHARSET).trim();
            tranCo = new String(buff, 23, 4, DEFAULT_CHARSET).trim();
            int signLen = Integer.parseInt(new String(buff, 27, 4, DEFAULT_CHARSET));
            int encryLen = totalLen - 15 - 4 - 4 - signLen;

            in.read(buff, 0, signLen);
            signBytes = ArrayUtils.subarray(buff, 0, signLen);

            in.read(buff, 0, encryLen);
            encryptedBytes = ArrayUtils.subarray(buff, 0, encryLen);
        } catch (Exception e) {
            throw new Exception("解析报文异常", e);
        }

        map.put("merchCo", merchCo);
        map.put("tranCo", tranCo);
        map.put("signBytes", signBytes);
        map.put("encryptedBytes", encryptedBytes);
        return map;
    }

}
