package com.kangyonggan.app.fortune.common.exception;

/**
 * 数据不合法
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class VaildParamsException extends Exception {

    public VaildParamsException() {
        super("数据不合法");
    }

    public VaildParamsException(String msg) {
        super(msg);
    }

    public VaildParamsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public VaildParamsException(Throwable cause) {
        super(cause);
    }
}

