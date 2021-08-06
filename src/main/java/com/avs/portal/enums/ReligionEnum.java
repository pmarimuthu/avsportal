package com.avs.portal.enums;

public enum ReligionEnum {
	
	HINDU("HINDU"), //
	OTHERS("OTHERS"); //

	private final String text;

	private ReligionEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
