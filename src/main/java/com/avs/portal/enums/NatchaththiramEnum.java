package com.avs.portal.enums;

public enum NatchaththiramEnum {
	
	ASHWINI_01("01. Ashwini (அஸ்வினி)"), // அஸ்வினி
	BHARANI_02("02. Bharani (பரணி)"), // பரணி
	KRITTIKA_03("03. Krittika (கார்த்திகை)"), // கார்த்திகை
	ROHINI_04("04. Rohini (ரோகிணி)"), // ரோகிணி
	MRIGASHIRA_05("05. Mrigashīra (மிருகசீரிடம்)"), // மிருகசீரிடம்
	ARDRA_06("06. Ardra (திருவாதிரை)"), // திருவாதிரை
	PUNARVASU_07("07. Punarvasu (புனர்பூசம்)"), // புனர்பூசம்
	PUSHYATISHYA_08("08. PushyaTishya (பூசம்)"), // பூசம்
	ASHLESHA_09("09. Ashleshā (ஆயில்யம்)"), // ஆயில்யம்
	MEGHA_10("10. Maghā (மகம்)"), // மகம்
	PURVAPHALGUNI_11("11. Pūrva Phalgunī (பூரம்)"), // பூரம்
	UTTARA_PHALGUNI_12("12. Uttara Phalgunī (உத்தரம்)"), // உத்தரம்
	HASTA_13("13. Hasta (அஸ்தம்)"), // அஸ்தம்
	CHITRA_14("14. Chitra (சித்திரை)"), // சித்திரை
	SWATI_15("15. Swāti (சுவாதி)"), // சுவாதி
	VISAKHA_16("16. Visakha (விசாகம்)"), // விசாகம்
	ANURADHA_17("17. Anuradha (அனுஷம்)"), // அனுஷம்
	JYESHTHA_18("18. Jyeshtha (கேட்டை)"), // கேட்டை
	MULA_19("19. Mula (மூலம்)"), // மூலம்
	PURVAASHADHA_20("20. Purva Ashadha (பூராடம்)"), // பூராடம்
	UTTARAASHADHA_21("21. Uttara Ashadha (உத்திராடம்)"), // உத்திராடம்
	SHRAVANA_22("22. Shravana (திருவோணம்)"), // திருவோணம்
	DHANISHTA_23("23. Dhanishta (அவிட்டம்)"), // அவிட்டம்
	SHANTABHISHA_24("24. Shatabhisha (சதயம்)"), // சதயம்
	PURVA_BHADRAPADA_25("25. Purva Bhadrapada (பூரட்டாதி)"), // பூரட்டாதி
	UTTARA_BHADRAPADA_26("26. Uttara Bhadrapada (உத்திரட்டாதி)"), // உத்திரட்டாதி
	REVATI_27("27. Revati (ரேவதி)"); // ரேவதி

	private final String natchaththiram;

	private NatchaththiramEnum(final String natchaththiram) {
		this.natchaththiram = natchaththiram;
	}

	@Override
	public String toString() {
		return this.natchaththiram;
	}
	
}
