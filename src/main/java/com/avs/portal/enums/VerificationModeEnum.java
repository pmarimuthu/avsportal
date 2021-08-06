package com.avs.portal.enums;

public enum VerificationModeEnum {
	
	PHONE("PHONE"), //
	MEMBER("MEMBER"), //
	EMAIL("EMAIL"); //

	private final String text;

	private VerificationModeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
