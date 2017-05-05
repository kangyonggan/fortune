package com.kangyonggan.app.fortune.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author kangyonggan
 * @since 4/7/17
 */
public class DateUtil {

    private static final String FULL_DATETIME_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * yyyyMMddHHmmssSSS
     *
     * @return
     */
    public static String getFullDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FULL_DATETIME_PATTERN));
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}
