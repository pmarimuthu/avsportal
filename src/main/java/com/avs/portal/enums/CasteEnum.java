package com.avs.portal.enums;

public enum CasteEnum {
	
	VELLALAR("VELLALAR"), //
	OTHERS("OTHERS"); //

	private final String text;

	private CasteEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
