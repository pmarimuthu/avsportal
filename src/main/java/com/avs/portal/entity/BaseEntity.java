package com.avs.portal.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.avs.portal.bean.BaseBean;

public class BaseEntity {
	
	@Transient
	private boolean hasError = false;
	
	@Transient
	private List<String> customErrorMessages = new ArrayList<>();
	
	@Transient
	private Throwable throwable;
	
	public boolean isHasError() {
		return hasError;
	}

	public BaseEntity setHasError(boolean hasError) {
		this.hasError = hasError;
		return this;
	}

	public List<String> getCustomErrorMessages() {
		return customErrorMessages;
	}

	public BaseEntity setCustomErrorMessages(List<String> customErrorMessages) {
		this.customErrorMessages = customErrorMessages;
		return this;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public BaseEntity setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	public BaseBean toBean() {
		return new BaseBean()
				.setHasError(hasError)
				.setCustomErrorMessages(customErrorMessages)
				.setThrowable(throwable);
	}


}
