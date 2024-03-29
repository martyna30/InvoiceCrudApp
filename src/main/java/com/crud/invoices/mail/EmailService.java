package com.crud.invoices.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void send(String to, String link) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(link, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm your email");
            mimeMessageHelper.setFrom("martyna.prawdzik@gmail.com");
            javaMailSender.send(mimeMessage);
            LOGGER.info("Email has been sent");
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }

    }
}

