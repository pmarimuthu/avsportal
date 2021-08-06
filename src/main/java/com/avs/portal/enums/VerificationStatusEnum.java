package com.avs.portal.enums;

public enum VerificationStatusEnum {
	
	NOT_VERIFIED("NOT_VERIFIED"), //
	VERIFIED_NOT_OK("VERIFIED_NOT_OK"), //
	VERIFIED_OK("VERIFIED_OK"); //

	private final String text;

	private VerificationStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
