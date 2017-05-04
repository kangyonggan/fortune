package com.kangyonggan.app.fortune.common.exception;

/**
 * 构建报文异常
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public class BuildException extends Exception {

    public BuildException() {
        super("构建报文异常");
    }

    public BuildException(String msg) {
        super(msg);
    }

    public BuildException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BuildException(Throwable cause) {
        super(cause);
    }
}

