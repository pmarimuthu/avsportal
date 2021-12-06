package com.avs.portal.mail;

import java.util.Properties;
import java.util.Random;

import com.avs.portal.bean.UserBean;

public class EmailService {

	public static String API_BASE_URL = "http://localhost:8080";
	
	public static String DEFAULT_CALLBACK_URL = "http://localhost:4000";
		
	/***
	 * Normally, To change any sensitive data, user needs OTP to authenticate.
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String sendOTP(UserBean user) throws Exception {

		if(user == null || user.getId() == null)
			return null;

		Properties props = SendEmailSSL.getProperties();
		props.put("kanaksan.user.uuid", user.getId());
		props.put("kanaksan.user.email", user.getEmail());
		props.put("kanaksan.user.phone", user.getPhone());
		props.put("kanaksan.email.message.banner.title", "OTP To Change account details");
		
		String otpString = null;
		
		try {
			String content = SendEmailSSL.getEmailContentTemplate();			
			otpString = generateOTP();
			
			content = 
					content
						.replace("kanaksan.email.message.banner.title", "OTP To Change account details")
						.replace("kanaksan.user.information.firstname", "Firstname")
						.replace("kanaksan.email.message.body.lineone", 
								"OTP to change your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account details.")
						.replace("kanaksan.email.message.body.linetwo", "This OTP is valid until HH:MM:SS")
						.replace("kanaksan.email.callback.url" , DEFAULT_CALLBACK_URL)
						
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
			SendEmailSSL.sendGmailSSL(props);
			
			return otpString;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	
	}

	/***
	 * Normally, POST CREATE we need Email confirmation from new user.
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void sendConfirmEmailAddress(UserBean user) throws Exception {
		if(user == null || user.getId() == null)
			return;

		Properties props = SendEmailSSL.getProperties();
		props.put("kanaksan.user.uuid", user.getId());
		props.put("kanaksan.user.email", user.getEmail());
		props.put("kanaksan.user.phone", user.getPhone());
		props.put("kanaksan.email.message.banner.title", "Confirm Email Address");
		
		try {
			String content = SendEmailSSL.getEmailContentTemplate();
			String callbackURL = API_BASE_URL + "/api/user/verify/email/" + user.getId().toString();
			
			content = 
					content
						.replace("kanaksan.email.message.banner.title", "Confirm Email Address")
						.replace("kanaksan.user.information.firstname", "Firstname")
						.replace("kanaksan.email.message.body.lineone", 
								"Please confirm your email address for your <span style=\"color: rgb(255, 105, 0); font-weight: bold;\">Kanaksan</span> account.")
						.replace("kanaksan.email.message.body.linetwo", "")
						.replaceAll("kanaksan.email.callback.url", callbackURL)
						.replace("kanaksan.email.callback.message", "Confirm Email Address")
						.replace("kanaksan.user.uuid", user.getId().toString())
						.replace("kanaksan.email.callback.blockORnone", "block")
						.replace("kanaksan.email.otp.blockOrNone", "none");

			props.put("kanaksan.mail.subject", "TestMail | Confirm Email Address");
			props.put("kanaksan.mail.content", content);
			SendEmailSSL.sendGmailSSL(props);
			
		} catch (Exception e) {
			e.printStackTrace();
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
