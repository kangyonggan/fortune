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
     * 默认不是调试模式
     */
    private static final boolean IS_DEBUG = false;

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
        return getPublicKey(publicKeyPath, IS_DEBUG);
    }

    /**
     * 获取对方公钥
     *
     * @param publicKeyPath 公钥绝对路径
     * @param isDebug       是否调试模式
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyPath, boolean isDebug) throws Exception {
        if (isDebug) {
            return null;
        }

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
            throw new Exception("加载对方公钥异常[" + publicKeyPath + "]", e);
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
        return getPrivateKey(privateKeyPath, IS_DEBUG);
    }

    /**
     * 获取己方私钥
     *
     * @param privateKeyPath 私钥绝对路径
     * @param isDebug        是否调试模式
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyPath, boolean isDebug) throws Exception {
        if (isDebug) {
            return null;
        }

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
            throw new Exception("加载己方私钥异常[" + privateKeyPath + "]", e);
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
        return sign(plain, privateKey, DEFAULT_CHARSET, IS_DEBUG);
    }

    /**
     * 签名
     *
     * @param plain      明文
     * @param privateKey 己方私钥
     * @param charset    字符集
     * @return
     * @throws Exception
     */
    public static byte[] sign(String plain, PrivateKey privateKey, String charset) throws Exception {
        return sign(plain, privateKey, charset, IS_DEBUG);
    }

    /**
     * 签名
     *
     * @param plain      明文
     * @param privateKey 己方私钥
     * @param isDebug    是否调试模式
     * @return
     * @throws Exception
     */
    public static byte[] sign(String plain, PrivateKey privateKey, boolean isDebug) throws Exception {
        return sign(plain, privateKey, DEFAULT_CHARSET, isDebug);
    }

    /**
     * 签名
     *
     * @param plain      明文
     * @param privateKey 己方私钥
     * @param charset    字符集
     * @param isDebug    是否调试模式
     * @return
     * @throws Exception
     */
    public static byte[] sign(String plain, PrivateKey privateKey, String charset, boolean isDebug) throws Exception {
        if (isDebug) {
            return plain.getBytes(charset);
        }
        byte plainBytes[] = plain.getBytes(charset);
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
        return isValid(plain, signBytes, publicKey, DEFAULT_CHARSET, IS_DEBUG);
    }

    /**
     * 验签
     *
     * @param plain     明文
     * @param signBytes 签名数据
     * @param publicKey 对方公钥
     * @param charset   字符集
     * @return
     * @throws Exception
     */
    public static boolean isValid(String plain, byte[] signBytes, PublicKey publicKey, String charset) throws Exception {
        return isValid(plain, signBytes, publicKey, charset, IS_DEBUG);
    }

    /**
     * 验签
     *
     * @param plain     明文
     * @param signBytes 签名数据
     * @param publicKey 对方公钥
     * @param isDebug   是否调试模式
     * @return
     * @throws Exception
     */
    public static boolean isValid(String plain, byte[] signBytes, PublicKey publicKey, boolean isDebug) throws Exception {
        return isValid(plain, signBytes, publicKey, DEFAULT_CHARSET, isDebug);
    }

    /**
     * 验签
     *
     * @param plain     明文
     * @param signBytes 签名数据
     * @param publicKey 对方公钥
     * @param charset   字符集
     * @param isDebug   是否调试模式
     * @return
     * @throws Exception
     */
    public static boolean isValid(String plain, byte[] signBytes, PublicKey publicKey, String charset, boolean isDebug) throws Exception {
        if (isDebug) {
            return true;
        }
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(plain.getBytes(charset));
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
        return encrypt(plain, publicKey, DEFAULT_CHARSET, IS_DEBUG);
    }

    /**
     * 加密
     *
     * @param plain     明文
     * @param publicKey 对方公钥
     * @param charset   字符集
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plain, PublicKey publicKey, String charset) throws Exception {
        return encrypt(plain, publicKey, charset, IS_DEBUG);
    }

    /**
     * 加密
     *
     * @param plain     明文
     * @param publicKey 对方公钥
     * @param isDebug   是否调试模式
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plain, PublicKey publicKey, boolean isDebug) throws Exception {
        return encrypt(plain, publicKey, DEFAULT_CHARSET, isDebug);
    }

    /**
     * 加密
     *
     * @param plain     明文
     * @param publicKey 对方公钥
     * @param charset   字符集
     * @param isDebug   是否调试模式
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plain, PublicKey publicKey, String charset, boolean isDebug) throws Exception {
        if (isDebug) {
            return plain.getBytes(charset);
        }

        byte plainBytes[] = plain.getBytes(charset);
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
            throw new Exception("加密异常", e);
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
        return decrypt(encryptedBytes, privateKey, DEFAULT_CHARSET, IS_DEBUG);
    }

    /**
     * 解密
     *
     * @param encryptedBytes 密文
     * @param privateKey     己方私钥
     * @param charset        字符集
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] encryptedBytes, PrivateKey privateKey, String charset) throws Exception {
        return decrypt(encryptedBytes, privateKey, charset, IS_DEBUG);
    }

    /**
     * 解密
     *
     * @param encryptedBytes 密文
     * @param privateKey     己方私钥
     * @param isDebug        是否调试模式
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] encryptedBytes, PrivateKey privateKey, boolean isDebug) throws Exception {
        return decrypt(encryptedBytes, privateKey, DEFAULT_CHARSET, isDebug);
    }

    /**
     * 解密
     *
     * @param encryptedBytes 密文
     * @param privateKey     己方私钥
     * @param charset        字符集
     * @param isDebug        是否调试模式
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] encryptedBytes, PrivateKey privateKey, String charset, boolean isDebug) throws Exception {
        if (isDebug) {
            return new String(encryptedBytes, charset);
        }

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
            return new String(out.toByteArray(), charset);
        } catch (Exception e) {
            throw new Exception("解密异常", e);
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
        sb.append(StringUtils.leftPad(String.valueOf(15 + 8 + 4 + signBytes.length + encryptedBytes.length), 8, "0"));// 域1 总长度
        sb.append(StringUtils.leftPad(merchCo, 15, " "));// 域2 商户号
        sb.append(StringUtils.leftPad(tranCo, 8, " "));// 域3 交易码
        sb.append(StringUtils.leftPad(String.valueOf(signBytes.length), 4, "0"));// 域4 签名长度

        byte[] bytes = null;
        bytes = ArrayUtils.addAll(bytes, sb.toString().getBytes());// 前4域的组合，没中文，无需指定字符集
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
            in.read(buff, 0, 35);
            int totalLen = Integer.parseInt(new String(buff, 0, 8));// 无中文，无需指定字符集
            merchCo = new String(buff, 8, 15).trim();
            tranCo = new String(buff, 23, 8).trim();
            int signLen = Integer.parseInt(new String(buff, 31, 4));
            int encryLen = totalLen - 15 - 8 - 4 - signLen;

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
