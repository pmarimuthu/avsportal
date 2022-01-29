package com.avs.portal.util;

public final class Logger {
	
	private Logger() {
	}

	public static void info(String message) {
		//System.out.println(message);
	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static void logError(String message) {
		System.err.println(message);
	}
}
