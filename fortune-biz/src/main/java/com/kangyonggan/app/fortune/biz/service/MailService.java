package com.kangyonggan.app.fortune.biz.service;

/**
 * @author kangyonggan
 * @since 4/12/17
 */
public interface MailService {

    /**
     * 发送邮件
     *
     * @param to
     * @param title
     * @param text
     */
    void send(String to, String title, String text);

}
