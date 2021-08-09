package com.avs.portal.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class CommonUtil {

	public static final String DATE_TIME_PATTERN1 = "EEEE d, MMM yy HH:mm:ss";
	
	public static final String DATE_TIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";
	
	
	public static boolean isValidPhone(Long phone) {
        if(phone != null 
        		&& String.valueOf(phone).length() == 10)
        	return true;
        
        return false;
    }
	
	public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        
        return pattern.matcher(email).matches();
    }
	
	public static String toString(Timestamp timestamp) {
		SimpleDateFormat formatter = new SimpleDateFormat(CommonUtil.DATE_TIME_PATTERN1, Locale.US);
		return formatter.format(timestamp);
	}
	
	public static String toString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonUtil.DATE_TIME_PATTERN1);
		return localDateTime.format(formatter);
	}

	public static LocalDateTime toLocalDateTimeOrNull(Timestamp timestamp) {
		if(null == timestamp)
			return null;
		
		return timestamp.toLocalDateTime();
	}

	public static String generateTempPassword() {
		return String.format("%06d", new Random().nextInt(1000000));
	}
	
}
