package com.avs.portal.enums;

public enum RelationToMeEnum {
	
	APPA("APPA"), //
	AMMA("AMMA"), //
	ANNAN("ANNAN"), //
	AKKAA("AKKAA"), //
	
	OTHER("OTHER"); //

	private final String text;

	private RelationToMeEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
