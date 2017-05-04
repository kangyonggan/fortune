package com.kangyonggan.app.fortune.common.exception;

/**
 * 加密异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class EncodeException extends Exception {

    public EncodeException() {
        super("报文加密异常");
    }

    public EncodeException(String msg) {
        super(msg);
    }

    public EncodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EncodeException(Throwable cause) {
        super(cause);
    }
}

