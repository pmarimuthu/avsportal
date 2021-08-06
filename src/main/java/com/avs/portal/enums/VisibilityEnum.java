package com.avs.portal.enums;

public enum VisibilityEnum {
	
	PRIVATE("PRIVATE"), // Visible to Self only
	FRIENDLY("FRIENDLY"), // Visible to connected users
	PUBLIC("PUBLIC"); // Visible to all users

	private final String text;

	private VisibilityEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
