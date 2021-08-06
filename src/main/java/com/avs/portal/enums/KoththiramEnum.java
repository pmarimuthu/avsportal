package com.avs.portal.enums;

public enum KoththiramEnum {
	
	KOTHTHIRAM_1("KOTHTHIRAM_1"), //
	KOTHTHIRAM_2("KOTHTHIRAM_2"); //

	private final String text;

	private KoththiramEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
