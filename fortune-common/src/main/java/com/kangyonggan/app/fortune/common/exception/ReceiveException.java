package com.kangyonggan.app.fortune.common.exception;

/**
 * 接收报文异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class ReceiveException extends Exception {

    public ReceiveException() {
        super("接收商户交易报文异常");
    }

    public ReceiveException(String msg) {
        super(msg);
    }

    public ReceiveException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ReceiveException(Throwable cause) {
        super(cause);
    }
}

