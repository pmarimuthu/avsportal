package com.avs.portal.enums;

public enum NotificationTypeEnum {
	
	INFORMATION("INFORMATION"), //
	ACTION("ACTION"), //
	WARNING("WARNING"), //
	ADVERTISEMENT("ADVERTISEMENT"); //

	private final String text;

	private NotificationTypeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
