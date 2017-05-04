package com.kangyonggan.app.fortune.common.exception;

/**
 * 解析报文异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class ParseException extends Exception {

    public ParseException() {
        super("解析商户报文异常");
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}

