package com.avs.portal.enums;

public enum LanguageEnum {
	
	ENGLISH("ENGLISH"), //
	TAMIL("TAMIL"); //

	private final String text;

	private LanguageEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
