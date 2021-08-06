package com.avs.portal.enums;

public enum MaritalStatusEnum {
	
	MARRIED("MARRIED"), //
	DIVORCED("DIVORCED"), //
	WIDOW("WIDOW"), //
	SINGLE("SINGLE"); //

	private final String text;

	private MaritalStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
