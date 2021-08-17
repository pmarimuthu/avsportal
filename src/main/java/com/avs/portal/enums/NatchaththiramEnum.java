package com.avs.portal.enums;

public enum NatchaththiramEnum {
	
	Ashwini_01("01. Ashwini (அஸ்வினி)"), // அஸ்வினி
	Bharani_02("02. Bharani (பரணி)"), // பரணி
	Krittika_03("03. Krittika (கார்த்திகை)"), // கார்த்திகை
	Rohini_04("04. Rohini (ரோகிணி)"), // ரோகிணி
	Mrigashīra_05("05. Mrigashīra (மிருகசீரிடம்)"), // மிருகசீரிடம்
	Ardra_06("06. Ardra (திருவாதிரை)"), // திருவாதிரை
	Punarvasu_07("07. Punarvasu (புனர்பூசம்)"), // புனர்பூசம்
	PushyaTishya_08("08. PushyaTishya (பூசம்)"), // பூசம்
	Ashleshā_09("09. Ashleshā (ஆயில்யம்)"), // ஆயில்யம்
	Maghā_10("10. Maghā (மகம்)"), // மகம்
	PūrvaPhalgunī_11("11. Pūrva Phalgunī (பூரம்)"), // பூரம்
	Uttara_Phalgunī_12("12. Uttara Phalgunī (உத்தரம்)"), // உத்தரம்
	Hasta_13("13. Hasta (அஸ்தம்)"), // அஸ்தம்
	Chitra_14("14. Chitra (சித்திரை)"), // சித்திரை
	Swāti_15("15. Swāti (சுவாதி)"), // சுவாதி
	Visakha_16("16. Visakha (விசாகம்)"), // விசாகம்
	Anuradha_17("17. Anuradha (அனுஷம்)"), // அனுஷம்
	Jyeshtha_18("18. Jyeshtha (கேட்டை)"), // கேட்டை
	Mula_19("19. Mula (மூலம்)"), // மூலம்
	PurvaAshadha_20("20. Purva Ashadha (பூராடம்)"), // பூராடம்
	UttaraAshadha_21("21. Uttara Ashadha (உத்திராடம்)"), // உத்திராடம்
	Shravana_22("22. Shravana (திருவோணம்)"), // திருவோணம்
	Dhanishta_23("23. Dhanishta (அவிட்டம்)"), // அவிட்டம்
	Shatabhisha_24("24. Shatabhisha (சதயம்)"), // சதயம்
	Purva_Bhadrapada_25("25. Purva Bhadrapada (பூரட்டாதி)"), // பூரட்டாதி
	UttaraBhadrapada_26("26. Uttara Bhadrapada (உத்திரட்டாதி)"), // உத்திரட்டாதி
	Revati_27("27. Revati (ரேவதி)"); // ரேவதி

	private final String natchaththiram;

	private NatchaththiramEnum(final String natchaththiram) {
		this.natchaththiram = natchaththiram;
	}

	@Override
	public String toString() {
		return this.natchaththiram;
	}
	
}
