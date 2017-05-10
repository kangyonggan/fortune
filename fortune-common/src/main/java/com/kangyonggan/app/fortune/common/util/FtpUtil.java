package com.kangyonggan.app.fortune.common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author kangyonggan
 * @since 2017/5/10 0010
 */
public class FtpUtil {

    /**
     * 登录ftp服务器
     *
     * @param host
     * @param username
     * @param password
     * @param uploadPath
     * @return
     * @throws Exception
     */
    private static FTPClient connect(String host, String username, String password, String uploadPath) throws Exception {
        FTPClient ftp = new FTPClient();
        int reply;
        ftp.connect(host, 23);
        ftp.login(username, password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null;
        }
        ftp.changeWorkingDirectory(uploadPath);
        return ftp;
    }

    /**
     * 向ftp服务器推送文件
     *
     * @param host
     * @param username
     * @param password
     * @param uploadPath
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String push(String host, String username, String password, String uploadPath, String filePath) throws Exception {
        FTPClient ftp = null;
        FileInputStream in = null;
        try {
            ftp = connect(host, username, password, uploadPath);
            File file = new File(filePath);
            in = new FileInputStream(file);
            ftp.storeFile(file.getName(), in);

            return file.getName();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftp != null) {
                ftp.disconnect();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
