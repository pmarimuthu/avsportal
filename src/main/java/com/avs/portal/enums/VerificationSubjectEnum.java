package com.avs.portal.enums;

public enum VerificationSubjectEnum {
	
	USER("USER"), //
	PROFILE("PROFILE"); //

	private final String text;

	private VerificationSubjectEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
