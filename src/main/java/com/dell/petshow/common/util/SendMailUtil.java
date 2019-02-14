/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author map6
 */
public class SendMailUtil {

    private static final String host = "localhost";
    public static final Logger LOG = LoggerFactory.getLogger(SendMailUtil.class);

    /**
     * Comman method to send mail
     * @param mailFrom  mail from
     * @param mailTo   multiple mail using comma to separate them
     * @param title  email title
     * @param content  email content
     */
    public static void sendMail(String mailFrom, String mailTo, String mailCC, String title, String content) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try {
            LOG.info("Begin sending email....");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCC));
            message.setSubject(title);
            message.setText(content,"utf-8","html");
            Transport.send(message);
            LOG.info("End sending email successfully....");
        } catch (Exception mex) {
            LOG.error("Send mail failed from {} to {}, please check server!",mailFrom, mailTo);
        }
    }

    public  void sendMail(String mailTo, String title, String content) {
        String mailFrom = "Dummy@dell.com";
        sendMail(mailFrom, mailTo, mailFrom, title, content);
    }
}
