package com.avs.portal.util;

import java.util.List;

import com.azure.communication.sms.SmsClient;
import com.azure.communication.sms.SmsClientBuilder;
import com.azure.communication.sms.models.SmsSendOptions;
import com.azure.communication.sms.models.SmsSendResult;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Context;

public class AzureSMSClientUtil {

	private static final String END_POINT = "https://<resource-name>.communication.azure.com/";

	private static final String ACCESS_KEY_CREDENTIAL = "<access-key-credential>";

	public static void main(String[] args) {

		SmsClient smsClient = AzureSMSClientUtil.getSmsClient(END_POINT, ACCESS_KEY_CREDENTIAL);
		AzureSMSClientUtil.send1To1Sms(smsClient, "+919886257568", "+919886257568", "Self Hello");

	}

	private static SmsClient getSmsClient(String connectionString) {

		return new SmsClientBuilder()
				.connectionString(connectionString)
				.buildClient();
	}

	private static SmsClient getSmsClient(String endpoint, String accessKeyCredential) {

		return new SmsClientBuilder()
				.endpoint(endpoint)
				.credential(new AzureKeyCredential(accessKeyCredential))
				.buildClient();
	}

	public static void send1To1Sms(SmsClient smsClient, String fromPhoneNumber, String toPhoneNumber, String message) {

		SmsSendResult sendResult = smsClient.send(fromPhoneNumber, toPhoneNumber, message);

		System.out.println("Message Id: " + sendResult.getMessageId());
		System.out.println("Recipient Number: " + sendResult.getTo());
		System.out.println("Send Result Successful:" + sendResult.isSuccessful());
	}

	public static void send1ToNSms(SmsClient smsClient, String fromPhoneNumber, List<String> toPhoneNumbers, String message, String optionalTag) {

		SmsSendOptions options = new SmsSendOptions();

		if(optionalTag != null) {
			options.setDeliveryReportEnabled(true);
			options.setTag(optionalTag);
		}

		Iterable<SmsSendResult> sendResults = smsClient.sendWithResponse(
				fromPhoneNumber,
				toPhoneNumbers,
				message,
				options /* Optional */,
				Context.NONE).getValue();

		for (SmsSendResult result : sendResults) {
			System.out.println("Message Id: " + result.getMessageId());
			System.out.println("Recipient Number: " + result.getTo());
			System.out.println("Send Result Successful:" + result.isSuccessful());
		}
	}

}
