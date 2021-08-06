package com.avs.portal.enums;

public enum NatchaththiramEnum {
	
	NATCHATHTHIRAM_1("NATCHATHTHIRAM_1"), //
	NATCHATHTHIRAM_2("NATCHATHTHIRAM_2"); //

	private final String text;

	private NatchaththiramEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
