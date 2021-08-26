package com.avs.portal.enums;

public enum RaasiEnum {
	
	Aries_01("Aries_01"), 	// or Maish
	Taurus_02("Taurus_02"), // or Vrish
	Gemini_03("Gemini_03"), // or Mithun
	Cancer_04("Cancer_04"), // or Kark
	Leo_05("Leo_05"), 		// or Singh
	Virgo_06("Virgo_06"), 	// or Kanya
	Libra_07("Libra_07"), 	// or Tula
	Scorpio_08("Scorpio_08"), // or Vrishchik
	Sagittarius_09("Sagittarius_09"), // or Dhanu
	Capricorn_10("Capricorn_10"), // or Makar
	Aquarius_11("Aquarius_11"), // or Kumbh
	Pisces_12("Pisces_12"); // or Meen
	
	private final String text;

	private RaasiEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
