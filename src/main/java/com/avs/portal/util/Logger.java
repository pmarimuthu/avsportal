package com.avs.portal.util;

import com.avs.portal.bean.LogMessage;
import com.avs.portal.enums.LogStatusEnum;

public final class Logger {
	
	private Logger() {
	}

	public static void log(LogStatusEnum status, String origin, String message) {
		LogMessage logMessage = new LogMessage()
				.setStatus(LogStatusEnum.SUCCESS)
				.setOrigin(origin)
				.setMessage(message);
		
		if(status == LogStatusEnum.ERROR)
			System.err.println(logMessage);
		else 
			System.out.println(logMessage);
	}
}
