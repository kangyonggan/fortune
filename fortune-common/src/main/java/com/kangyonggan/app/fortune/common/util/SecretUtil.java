package com.kangyonggan.app.fortune.common.util;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Log4j2
public class SecretUtil {

    /**
     * 加载公钥
     *
     * @param publicKeyPath
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyPath) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(publicKeyPath);
            return CryptoUtil.getPublicKey(inputStream, "RSA");
        } catch (Exception e) {
            throw new Exception("无法加载公钥[" + publicKeyPath + "]", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭流失败", e);
            }
        }
    }

    /**
     * 加载私钥
     *
     * @param privateKeyPath
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(privateKeyPath);
            return CryptoUtil.getPrivateKey(inputStream, "RSA");
        } catch (Exception e) {
            throw new Exception("无法加载私钥[" + privateKeyPath + "]", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭流失败", e);
            }
        }
    }

}
