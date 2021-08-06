package com.avs.portal.enums;

public enum RaasiEnum {
	
	RAASI_1("RAASI_1"), //
	RAASI_2("RAASI_2"); //

	private final String text;

	private RaasiEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
