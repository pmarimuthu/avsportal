package com.avs.portal.enums;

public enum FamilyMemberTitleEnum {
	
	HEAD("HEAD"), //
	SPOUSE("SPOUSE"), //
	SON("SON"), //
	DAUGHTER("DAUGHTER"); //

	private final String text;

	private FamilyMemberTitleEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
