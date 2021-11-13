package com.avs.portal.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailSSL {

    public static void main(String[] args) {

        final String username = "pmarimuthu@gmail.com";
        final String password = "LaKsHiTh$2709&AnAnYa^0212"; //
        final String appPassword = "fgldckqoagfgyesu";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@kanksan.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("team.kanaksan@gmail.com, pmarimuthu@gmail.com")
            );
            message.setSubject("Testing Gmail SSL");
            String content = "Dear &lt;User&gt;, <br><br> Welcome to Kanaksan!!!<hr><button>Confirm</button>";
            message.setContent(content, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

