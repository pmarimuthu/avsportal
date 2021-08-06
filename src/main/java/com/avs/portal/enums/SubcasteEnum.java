package com.avs.portal.enums;

public enum SubcasteEnum {
	
	ARUNATTU_VELLALAR("ARUNATTU_VELLALAR"), //
	OTHERS("OTHERS"); //

	private final String text;

	private SubcasteEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
