package com.avs.portal.enums;

public enum UserReferralStatusEnum {
	
	AVAILED("AVAILED"), //
	UNAVAILED("UNAVAILED"); //

	private final String text;

	private UserReferralStatusEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
