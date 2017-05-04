package com.kangyonggan.app.fortune.common.exception;

/**
 * 解密异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class DecodeException extends Exception {

    public DecodeException() {
        super("报文解密异常");
    }

    public DecodeException(String msg) {
        super(msg);
    }

    public DecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DecodeException(Throwable cause) {
        super(cause);
    }
}

