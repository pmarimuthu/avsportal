package com.avs.portal.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;

public class SendEmailSSL {

	public static void main(String[] args) throws Exception {
	}
	
	public static Properties getProperties() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		props.put("mail.smtp.username", "pmarimuthu@gmail.com");
		props.put("mail.smtp.appPassword", "fgldckqoagfgyesu");

		props.put("kanaksan.mail.from", "pmarimuthu@gmail.com");
		props.put("kanaksan.mail.csvRecipients", "pmarimuthu@gmail.com, team.kanaksan@gmail.com");
		
		props.put("kanaksan.mail.subject", "TestMail" + new Date().toString());
		props.put("kanaksan.mail.content.mimeType", "text/html");
		props.put("kanaksan.mail.content", getEmailContentTemplate());

		props.put("<content-id>", "<logocid>");
		props.put("kanaksan.mail.attach.file", "D:\\Tutorials\\avsportal\\src\\main\\resources\\static\\assets\\logo.PNG");

		
		return props;
	}

	public static String getEmailContentTemplate() throws Exception {
		File file = new File("D:\\Tutorials\\avsportal\\src\\main\\resources\\static\\assets\\mail.html");
		//new File(new SendEmailSSL().getClass().getResource("assets/mail.html").getFile());
		InputStream inputStream = new FileInputStream(file);
		return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
	}

	public static void sendGmailSSL(Properties props) throws Exception {
		
		Authenticator authenticator = new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.smtp.username"), props.getProperty("mail.smtp.appPassword"));
			}
		};
		
		Session session = Session.getInstance(props, authenticator);

		// Message
		Message message = new MimeMessage(session);

		// Properties
		message.setFrom(new InternetAddress(props.getProperty("kanaksan.mail.from")));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(props.getProperty("kanaksan.mail.csvRecipients")));
		message.setSubject(props.getProperty("kanaksan.mail.subject"));
		message.setSentDate(new Date());

		// MessageBody
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(props.getProperty("kanaksan.mail.content"), props.get("kanaksan.mail.content.mimeType").toString());

		// Message Multipart
		Multipart multipart = new MimeMultipart();

		// Image Part
		MimeBodyPart imagePart = new MimeBodyPart();
		imagePart.setHeader("Content-ID", props.getProperty("<content-id>"));
		imagePart.setDisposition(MimeBodyPart.INLINE);
		imagePart.attachFile(props.getProperty("kanaksan.mail.attach.file"));

		// Prepare & Send
		multipart.addBodyPart(bodyPart);
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		Transport.send(message);
	}
}

