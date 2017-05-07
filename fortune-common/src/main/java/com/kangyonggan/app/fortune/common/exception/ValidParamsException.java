package com.kangyonggan.app.fortune.common.exception;

/**
 * 数据不合法
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class ValidParamsException extends Exception {

    public ValidParamsException() {
        super("数据不合法");
    }

    public ValidParamsException(String msg) {
        super(msg);
    }

    public ValidParamsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidParamsException(Throwable cause) {
        super(cause);
    }
}

