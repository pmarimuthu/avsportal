package com.avs.portal.enums;

public enum DeviceTypeEnum {
	
	MOBILE("MOBILE"), //
	DESKTOP("DESKTOP"), //
	TABLET("TABLET"), //
	OTHER("OTHER"); //

	private final String text;

	private DeviceTypeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
