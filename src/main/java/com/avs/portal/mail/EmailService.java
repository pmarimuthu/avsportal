package com.avs.portal.mail;

import java.util.Properties;
import java.util.Random;

import com.avs.portal.bean.UserBean;
import com.avs.portal.enums.LogStatusEnum;
import com.avs.portal.exception.AVSApplicationException;
import com.avs.portal.util.Constants;
import com.avs.portal.util.Logger;

public class EmailService {

	private static final String KANAKSAN_USER_UUID = "kanaksan.user.uuid";
	private static final String KANAKSAN_USER_EMAIL = "kanaksan.user.email";
	private static final String KANAKSAN_USER_PHONE = "kanaksan.user.phone";
	private static final String KANAKSAN_USER_INFORMATION_FIRSTNAME = "kanaksan.user.information.firstname";
	private static final String KANAKSAN_EMAIL_MESSAGE_BANNER_TITLE = "kanaksan.email.message.banner.title";
	private static final String KANAKSAN_EMAIL_OTP_BLOCK_OR_NONE = "kanaksan.email.otp.blockOrNone";
	private static final String KANAKSAN_EMAIL_CALLBACK_BLOCK_OR_NONE = "kanaksan.email.callback.blockORnone";
	private static final String KANAKSAN_EMAIL_SUBJECT = "kanaksan.mail.subject";
	private static final String KANAKSAN_EMAIL_MESSAGE_BODY_LINEONE = "kanaksan.email.message.body.lineone";
	private static final String KANAKSAN_EMAIL_MESSAGE_BODY_LINETWO = "kanaksan.email.message.body.linetwo";
	
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

		props.put(KANAKSAN_USER_UUID, user.getId());
		props.put(KANAKSAN_USER_EMAIL, user.getEmail());
		props.put(KANAKSAN_USER_PHONE, user.getPhone());
		props.put(KANAKSAN_EMAIL_MESSAGE_BANNER_TITLE, "OTP To Change account details");

		String content = SendEmailSSL.getEmailContentTemplate(Constants.HTML_TEMPLATE_FILE_PATH);			
		String otpString = generateOTP();

		content = 
				content
				.replace(KANAKSAN_EMAIL_MESSAGE_BANNER_TITLE, "OTP To Change account details")
				.replace(KANAKSAN_USER_INFORMATION_FIRSTNAME, "Firstname")
				.replace(KANAKSAN_EMAIL_MESSAGE_BODY_LINEONE, 
						"OTP to change your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account details.")
				.replace(KANAKSAN_EMAIL_MESSAGE_BODY_LINETWO, "This OTP is valid until HH:MM:SS")
				.replace("kanaksan.email.callback.url" , Constants.DEFAULT_CALLBACK_URL)

				.replace(KANAKSAN_EMAIL_CALLBACK_BLOCK_OR_NONE, "none")
				.replace("kanaksan.email.callback.message", "Message Callback")
				.replace(KANAKSAN_USER_UUID, user.getId().toString())

				.replace(KANAKSAN_EMAIL_CALLBACK_BLOCK_OR_NONE, "none")
				.replace(KANAKSAN_EMAIL_OTP_BLOCK_OR_NONE, "block")

				.replace("kanaksan.email.otp.1of4", String.valueOf(otpString.charAt(0)) )
				.replace("kanaksan.email.otp.2of4", String.valueOf(otpString.charAt(1)) )
				.replace("kanaksan.email.otp.3of4", String.valueOf(otpString.charAt(2)) )
				.replace("kanaksan.email.otp.4of4", String.valueOf(otpString.charAt(3)) );

		props.put(KANAKSAN_EMAIL_SUBJECT, "TestMail | OTP to Reset Kanaksan password");
		props.put("kanaksan.mail.content", content);
		props.put("kanaksan.mail.OTP", otpString);
		Logger.log(LogStatusEnum.SUCCESS, "sendOTP", "OTP: " + otpString);

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
			Logger.log(LogStatusEnum.ERROR, "sendConfirmEmailAddress >", e.getMessage());
			throw e;
		}

		props.put(KANAKSAN_USER_UUID, user.getId());
		props.put(KANAKSAN_USER_EMAIL, user.getEmail());
		props.put(KANAKSAN_USER_PHONE, user.getPhone());
		props.put(KANAKSAN_EMAIL_MESSAGE_BANNER_TITLE, "Confirm Email Address");

		String content = "";
		
		try {
			content = SendEmailSSL.getEmailContentTemplate(Constants.HTML_TEMPLATE_FILE_PATH);
		} catch (AVSApplicationException e) {
			Logger.log(LogStatusEnum.ERROR, "sendConfirmEmailAddress > SendEmailSSL.getEmailContentTemplate", e.getMessage());
			throw e;
		}
		String callbackURL = Constants.API_BASE_URL + "/api/user/verify/email/" + user.getId().toString();

		content = 
				content
				.replace(KANAKSAN_EMAIL_MESSAGE_BANNER_TITLE, "Confirm Email Address")
				.replace(KANAKSAN_USER_INFORMATION_FIRSTNAME, user.getEmail())
				.replace(KANAKSAN_EMAIL_MESSAGE_BODY_LINEONE, 
						"Please confirm your email address for your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account.")
				.replace(KANAKSAN_EMAIL_MESSAGE_BODY_LINETWO, "")
				.replaceAll("kanaksan.email.callback.url", callbackURL)
				.replace("kanaksan.email.callback.message", "Your Login Credential: " + user.getUserCredential().getPassword())
				.replace(KANAKSAN_USER_UUID, user.getId().toString())
				.replace(KANAKSAN_EMAIL_CALLBACK_BLOCK_OR_NONE, "block")
				.replace(KANAKSAN_EMAIL_OTP_BLOCK_OR_NONE, "none");

		props.put(KANAKSAN_EMAIL_SUBJECT, "TestMail | Confirm Email Address");
		props.put("kanaksan.mail.content", content);

		try {
			SendEmailSSL.sendGmailSSL(props);
		} catch (AVSApplicationException e) {
			Logger.log(LogStatusEnum.ERROR, "sendConfirmEmailAddress > SendEmailSSL.sendGmailSSL", e.getMessage());
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
