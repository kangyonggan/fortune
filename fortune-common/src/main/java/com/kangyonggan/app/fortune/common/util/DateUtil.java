package com.kangyonggan.app.fortune.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author kangyonggan
 * @since 4/7/17
 */
public class DateUtil {

    private static final String FULL_DATETIME_PATTERN = "yyyyMMddHHmmssSSS";

    public static String getCurrentFullDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FULL_DATETIME_PATTERN));
    }
}
