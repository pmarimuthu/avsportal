package com.avs.portal.enums;

public enum AddressTypeEnum {
	
	NATIVE("NATIVE"), // 0
	LIVING("LIVING"), // 1
	OFFICE("OFFICE"); // 2

	private final String text;

	private AddressTypeEnum(final String text) {
		this.text = text;
	}	

	@Override
	public String toString() {
		return this.text;
	}
}
