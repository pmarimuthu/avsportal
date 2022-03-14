package com.avs.portal.enums;

public enum LogStatusEnum {
	
	SUCCESS("SUCCESS"),
	ERROR("ERROR"),
	INFO("INFO");

	private final String text;

	private LogStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}

