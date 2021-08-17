package com.avs.portal.enums;

public enum KoththiramEnum {
	
	AALATHTHUDAIYAAN_01("01. ஆலத்துடையான்"), //ஆலத்துடையான்
	ETHUMALUDAYAAN_02("02. எதுமலுடையான்"), //எதுமலுடையான்
	KALATHTHUDAIYAAN_03("03. களத்துடையான்"), //களத்துடையான்
	K_VALAMUDAIYAAN_04("04. களப்பான் (வளமுடையான்)"), //களப்பான் (வளமுடையான்)
	KAARUDAIYAAN_05("05. காருடையான்"), //காருடையான்
	GUNAKKOTHTHUDAIYAAN_06("06. குணக்கொத்துடையான்"), //குணக்கொத்துடையான்
	KURUVALUDAIYAAN_07("07. குருவலுடையான்"), //குருவலுடையான்
	KOOTHTHUDAIYAAN_08("08. கூத்துடையான்"), //கூத்துடையான்
	KONNAKKUTTAIYAAN_09("09. கொன்னக்குட்டையான்"), //கொன்னக்குட்டையான்
	KOATTUDAIYAAN_10("10. கோட்டுடையான்"), //கோட்டுடையான்
	KOONUDAIYAAN_11("11. கோனுடையான்"), //கோனுடையான்
	SAMAYAMANTHIRI_12("12. சமயமந்திரி"), //சமயமந்திரி
	SANAMANGALATHTHUDAIYAAN_13("13. சனமங்கலத்துடையான்"), //சனமங்கலத்துடையான்
	SAATHTHUDAIYAAN_14("14. சாத்துடையான்"), //சாத்துடையான்
	SIRUTHALUDAIYAAN_15("15. சிறுதலுடையான்"), //சிறுதலுடையான்
	THIRICHCHANGUDAIYAAN_16("16. திருச்சங்குடையான்"), //திருச்சங்குடையான்
	THETHTHU_MANGALATHTHUDAIYAAN_17("17. தெத்தமங்கலத்துடையான்"), //தெத்தமங்கலத்துடையான்
	THEVANGUDAIYAAN_18("18. தேவங்குடையான்"), //தேவங்குடையான்
	NATHTHAMUDAIYAAN_19("19. நத்தமுடையான்"), //நத்தமுடையான்
	NALLUDAIYAAN_20("20. நல்லுடையான்"), //நல்லுடையான்
	NIMMALUDAIYAAN_21("21. நிம்மலுடையான்"), //நிம்மலுடையான்
	PANAIYADIYAAN_22("22. பனையடியான்"), //பனையடியான்
	PAAVALUDAIYAAN_23("23. பாவலுடையான்"), //பாவலுடையான்
	POONDILUDAIYAAN_24("24. பூண்டிலுடையான்"), //பூண்டிலுடையான்
	MARUTHUDAIYAAN_25("25. மருதுடையான்"), //மருதுடையான்
	MAATHTHUDAIYAAN_26("26. மாத்துடையான்"), //மாத்துடையான்
	KURUVALUDAIYAAN_27("27. மிரட்டுடையான் (குருவலுடையான்)"), //மிரட்டுடையான் (குருவலுடையான்)
	MURUKKATHTHUDAIYAAN_28("28. முருக்கத்துடையான்"), //முருக்கத்துடையான்
	VALAMUDAIYAAN_29("29. வளவுடையான்"), //வளவுடையான்
	VILVARAAYAN("30. வில்வராயன்"), //வில்வராயன்
	VENNAAVALUDAIYAAN_31("31. வெண்ணாவலுடையான்"), //வெண்ணாவலுடையான்
	CHAKRAVARTHI_32("32. சக்கரவர்த்தி"); //சக்கரவர்த்தி

	private final String koththiram;

	private KoththiramEnum(final String koththiram) {
		this.koththiram = koththiram;
	}

	@Override
	public String toString() {
		return this.koththiram;
	}
}
