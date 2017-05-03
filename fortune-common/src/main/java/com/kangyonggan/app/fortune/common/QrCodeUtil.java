package com.kangyonggan.app.fortune.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码
 *
 * @author kangyonggan
 * @since 4/24/17
 */
public class QrCodeUtil {

    /**
     * 生成二维码
     *
     * @param name
     * @param content
     * @param width
     * @throws WriterException
     * @throws IOException
     */
    public static void genQrCode(String name, String content, int width) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, width, hints);

        Path path = FileSystems.getDefault().getPath(name);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
    }
}
