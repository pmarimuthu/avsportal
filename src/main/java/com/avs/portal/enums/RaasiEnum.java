package com.avs.portal.enums;

public enum RaasiEnum {
	
	ARIES_01("Aries_01"), 	// or Maish
	TAURUS_02("Taurus_02"), // or Vrish
	GEMINI_03("Gemini_03"), // or Mithun
	CANCER_04("Cancer_04"), // or Kark
	LEO_05("Leo_05"), 		// or Singh
	VIRGO_06("Virgo_06"), 	// or Kanya
	LIBRA_07("Libra_07"), 	// or Tula
	SCORPIO_08("Scorpio_08"), // or Vrishchik
	SAGITTARIUS_09("Sagittarius_09"), // or Dhanu
	CARPRICORN_10("Capricorn_10"), // or Makar
	AQUARIUS_11("Aquarius_11"), // or Kumbh
	PISCES_12("Pisces_12"); // or Meen
	
	private final String text;

	private RaasiEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
