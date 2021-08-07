package com.avs.portal.enums;

public enum LoginKeyEnum {
	
	UUID("UUID"), //
	PHONE("PHONE"), //
	EMAIL("EMAIL");

	private final String text;

	private LoginKeyEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
