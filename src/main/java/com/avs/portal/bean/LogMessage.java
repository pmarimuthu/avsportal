package com.avs.portal.bean;

import java.io.Serializable;

import com.avs.portal.enums.LogStatusEnum;

public class LogMessage implements Serializable {

	private static final long serialVersionUID = 5309357245531063047L;

	private LogStatusEnum status = null;
	
	private String origin = null;
	
	private String message = null;

	public LogStatusEnum getStatus() {
		return status;
	}

	public LogMessage setStatus(LogStatusEnum status) {
		this.status = status;
		return this;
	}

	public String getOrigin() {
		return origin;
	}

	public LogMessage setOrigin(String origin) {
		this.origin = origin;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public LogMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return "LogMessage [status=" + status + ", origin=" + origin + ", message=" + message + "]";
	}
	
}
