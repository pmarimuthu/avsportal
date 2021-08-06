package com.avs.portal.enums;

public enum RoleEnum {
	
	ADMIN("ADMIN"), //
	ANALYST("ANALYST"), //
	USER("USER"); //

	private final String text;

	private RoleEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
