package com.avs.portal.enums;

public enum AddressTypeEnum {
	
	NATIVE("NATIVE"), //
	LIVING("LIVING"), //
	OFFICE("OFFICE"); //

	private final String text;

	private AddressTypeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
