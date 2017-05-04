package com.kangyonggan.app.fortune.common.exception;

/**
 * 发送报文异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class SendException extends Exception {

    public SendException() {
        super("给商户发送交易报文异常");
    }

    public SendException(String msg) {
        super(msg);
    }

    public SendException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SendException(Throwable cause) {
        super(cause);
    }
}

