package com.avs.portal.enums;

public enum LiveStatusEnum {
	
	ALIVE("ALIVE"), //
	EXPIRED("EXPIRED"), //
	UNKNOWN("UNKNOWN"); //

	private final String text;

	private LiveStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
