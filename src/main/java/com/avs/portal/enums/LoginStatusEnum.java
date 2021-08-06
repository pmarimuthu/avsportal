package com.avs.portal.enums;

public enum LoginStatusEnum {
	
	LOGGED_IN("LOGGED_IN"), //
	LOGGED_OUT("LOGGED_OUT"), //
	FAILED("FAILED"); //

	private final String text;

	private LoginStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
