/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class MailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

    public static Properties mailProperties() {
        Properties property = new Properties();
        property.put("mail.smtp.auth", "true");
        property.put("mail.smtp.starttls.enable", "true");
        property.put("mail.smtp.host", Constants.GMAIL_MAIL_SMTP_HOST);
        property.put("mail.smtp.port", Constants.GMAIL_MAIL_SMTP_PORT);
        return property;
    }

    public static void generateMail(String recipientEmail, String mailSubject, String emailContent) {
        LOGGER.info("Preparing to send the email to " + recipientEmail);
        Session mailSession = Session.getInstance(mailProperties(),
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.GMAIL_MAIL_USERNAME, Constants.GMAIL_MAIL_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(Constants.MAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject(mailSubject);
            message.setText(emailContent);
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Error occured while sending mail :" + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
