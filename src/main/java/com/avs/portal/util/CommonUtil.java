package com.avs.portal.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

public class CommonUtil {

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
		String pattern = "EEE d MMM yy"; //"E, d M y, H:m:s S Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
		return formatter.format(timestamp);
	}
	
	public static String toString(LocalDateTime localDateTime) {
		String pattern = "yyyy-MM-dd HH:mm:ss"; //"EEE d MMM yy"; //"E, d M y, H:m:s S Z";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}

	public static LocalDateTime toLocalDateTimeOrNull(Timestamp timestamp) {
		if(null == timestamp)
			return null;
		
		return timestamp.toLocalDateTime();
	}
	
}
