package com.avs.portal.mail;

import java.util.Properties;
import java.util.Random;

import com.avs.portal.bean.UserBean;
import com.avs.portal.exception.AVSApplicationException;
import com.avs.portal.util.Constants;
import com.avs.portal.util.Logger;

public class EmailService {

	private EmailService() {
	}

	/***
	 * Normally, To change any sensitive data, user needs OTP to authenticate.
	 * 
	 * @param user
	 * @return
	 * @throws AVSApplicationException
	 */
	public static String sendOTP(UserBean user) throws AVSApplicationException {

		if(user == null || user.getId() == null)
			return null;

		Properties props = new Properties();

		try {
			props = SendEmailSSL.getProperties();
		} catch (Exception e) {
			throw new AVSApplicationException(e.getMessage(), e);
		}

		props.put("kanaksan.user.uuid", user.getId());
		props.put("kanaksan.user.email", user.getEmail());
		props.put("kanaksan.user.phone", user.getPhone());
		props.put("kanaksan.email.message.banner.title", "OTP To Change account details");

		String content = SendEmailSSL.getEmailContentTemplate();			
		String otpString = generateOTP();

		content = 
				content
				.replace("kanaksan.email.message.banner.title", "OTP To Change account details")
				.replace("kanaksan.user.information.firstname", "Firstname")
				.replace("kanaksan.email.message.body.lineone", 
						"OTP to change your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account details.")
				.replace("kanaksan.email.message.body.linetwo", "This OTP is valid until HH:MM:SS")
				.replace("kanaksan.email.callback.url" , Constants.DEFAULT_CALLBACK_URL)

				.replace("kanaksan.email.callback.blockORnone", "none")
				.replace("kanaksan.email.callback.message", "Message Callback")
				.replace("kanaksan.user.uuid", user.getId().toString())

				.replace("kanaksan.email.callback.blockORnone", "none")
				.replace("kanaksan.email.otp.blockOrNone", "block")

				.replace("kanaksan.email.otp.1of4", String.valueOf(otpString.charAt(0)) )
				.replace("kanaksan.email.otp.2of4", String.valueOf(otpString.charAt(1)) )
				.replace("kanaksan.email.otp.3of4", String.valueOf(otpString.charAt(2)) )
				.replace("kanaksan.email.otp.4of4", String.valueOf(otpString.charAt(3)) );

		props.put("kanaksan.mail.subject", "TestMail | OTP to Reset Kanaksan password");
		props.put("kanaksan.mail.content", content);
		props.put("kanaksan.mail.OTP", otpString);
		Logger.log("OTP: " + otpString);

		SendEmailSSL.sendGmailSSL(props);

		return otpString;

	}

	/***
	 * Normally, POST CREATE we need Email confirmation from new user.
	 * 
	 * @param user
	 * @throws AVSApplicationException
	 */
	public static void sendConfirmEmailAddress(UserBean user) throws AVSApplicationException {
		if(user == null || user.getId() == null)
			return;

		Properties props = new Properties(); 
		try {
			props = SendEmailSSL.getProperties();
		} catch (AVSApplicationException e) {
			Logger.logError(e.getMessage());
			throw e;
		}

		props.put("kanaksan.user.uuid", user.getId());
		props.put("kanaksan.user.email", user.getEmail());
		props.put("kanaksan.user.phone", user.getPhone());
		props.put("kanaksan.email.message.banner.title", "Confirm Email Address");

		String content = new String();
		try {
			content = SendEmailSSL.getEmailContentTemplate();
		} catch (AVSApplicationException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		String callbackURL = Constants.API_BASE_URL + "/api/user/verify/email/" + user.getId().toString();

		content = 
				content
				.replace("kanaksan.email.message.banner.title", "Confirm Email Address")
				.replace("kanaksan.user.information.firstname", user.getEmail())
				.replace("kanaksan.email.message.body.lineone", 
						"Please confirm your email address for your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account.")
				.replace("kanaksan.email.message.body.linetwo", "")
				.replaceAll("kanaksan.email.callback.url", callbackURL)
				.replace("kanaksan.email.callback.message", "Your Login Credential: " + user.getUserCredential().getPassword())
				.replace("kanaksan.user.uuid", user.getId().toString())
				.replace("kanaksan.email.callback.blockORnone", "block")
				.replace("kanaksan.email.otp.blockOrNone", "none");

		props.put("kanaksan.mail.subject", "TestMail | Confirm Email Address");
		props.put("kanaksan.mail.content", content);
		Logger.info("About to send email");

		try {
			SendEmailSSL.sendGmailSSL(props);
		} catch (AVSApplicationException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
	}

	private static String generateOTP() {
		StringBuilder otpBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			otpBuilder.append(random.nextInt(10));
		}

		return otpBuilder.toString();
	}

}
