package com.avs.portal.exception;

public class AVSApplicationException extends Exception {

	private static final long serialVersionUID = -1878464454801970207L;
	
	public AVSApplicationException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
