package com.kangyonggan.app.fortune.common.exception;

/**
 * 必填域缺失
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class EmptyParamsException extends Exception {

    public EmptyParamsException() {
        super("必填域缺失");
    }

    public EmptyParamsException(String msg) {
        super(msg);
    }

    public EmptyParamsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmptyParamsException(Throwable cause) {
        super(cause);
    }
}

