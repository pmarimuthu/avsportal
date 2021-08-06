package com.avs.portal.enums;

public enum UserAgentEnum {
	
	IE("IE"), //
	CHROME("CHROME"), //
	FIREFOX("FIREFOX"), //
	OTHER("OTHER"); //

	private final String text;

	private UserAgentEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
