package com.avs.portal.enums;

public enum GenderEnum {
	
	MALE("MALE"), //
	FEMALE("FEMALE"); //

	private final String text;

	private GenderEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
