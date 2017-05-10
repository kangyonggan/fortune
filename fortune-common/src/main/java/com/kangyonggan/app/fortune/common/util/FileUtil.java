package com.kangyonggan.app.fortune.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author kangyonggan
 * @since 2017/5/10 0010
 */
public class FileUtil {

    /**
     * 写文本文件到本地服务器
     *
     * @param filePath
     * @param fileContent
     * @throws Exception
     */
    public static void writeTextToFile(String filePath, String fileContent) throws Exception {
        BufferedWriter writer = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileContent);
            writer.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
