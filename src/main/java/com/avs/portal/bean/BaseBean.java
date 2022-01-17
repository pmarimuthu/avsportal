package com.avs.portal.bean;

import java.util.ArrayList;
import java.util.List;

public class BaseBean {

	private boolean hasError = false;
	
	private List<String> customErrorMessages = new ArrayList<>();
	
	private Throwable throwable;
	
	public boolean getHasError() {
		return hasError;
	}

	public BaseBean setHasError(boolean hasError) {
		this.hasError = hasError;
		return this;
	}

	public List<String> getCustomErrorMessages() {
		return customErrorMessages;
	}

	public BaseBean setCustomErrorMessages(List<String> customErrorMessages) {
		this.customErrorMessages = customErrorMessages;
		return this;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public BaseBean setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}

	@Override
	public String toString() {
		return "\n AbstractBean [ " +
				"Has Error: " + hasError + 
				", Custom Error Messages: " + customErrorMessages + 
				", Throwable: "	+ throwable + 
				"\n ]";
	}
	
}
