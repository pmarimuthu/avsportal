package com.avs.portal.enums;

public enum ReferralTypeEnum {
	
	REFERREDBY("Referred_By"), //
	REFERRER("Referrer"); //

	private final String text;

	private ReferralTypeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
