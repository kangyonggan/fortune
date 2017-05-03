package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.MailService;
import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author kangyonggan
 * @since 4/12/17
 */
@Service
@Log4j2
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @LogTime
    public void send(String to, String title, String text) {
        send(to, title, text, false);
    }

    /**
     * 发送邮件
     *
     * @param to
     * @param title
     * @param text
     * @param isHtml
     */
    private void send(String to, String title, String text, boolean isHtml) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            log.info("发件人邮箱：{}", PropertiesUtil.getProperties("mail.username"));
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(PropertiesUtil.getProperties("mail.username"), PropertiesUtil.getProperties("app.name"));
            helper.setTo(to);
            helper.setSubject(title);

            helper.setText(text, isHtml);
        } catch (Exception e) {
            log.error("邮件发送失败！" + to, e);
            return;
        }

        log.info("正在给{}发邮件...", to);
        try {
            javaMailSender.send(msg);
            log.info("邮件发送成功...");
        } catch (Exception e) {
            log.error("邮件发送失败", e);
        }
    }
}
