package com.avs.portal.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

public class CommonUtil {

	public static final String DATE_TIME_PATTERN1 = "EEEE d, MMM yy HH:mm:ss";
	
	public static final String DATE_TIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PASSWORD_REGEX = "^" + 
			"(?=.*[0-9])" +
			"(?=.*[a-z])" + 
			"(?=.*[A-Z])" +
			"(?=.*[!@#$%^&*])" +
			"(?=\\S+$).{8,20}$";
	
	

	
	private CommonUtil() {
	}
	
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

	public static String generateSixDigits() {
		return String.format("%06d", new Random().nextInt(1000000));
	}

	// "aaAA@@11"
	public static String generateDefaultPassword() {
		String lowerChar = RandomStringUtils.randomAlphabetic(1).toLowerCase();
		String upperChar = lowerChar.toUpperCase();
		String specialChar = generateSpecialChar();
		String number = RandomStringUtils.randomNumeric(1);
				
		return String.format("%8s", 
					lowerChar + lowerChar
					+ upperChar + upperChar
					+ specialChar + specialChar
					+ number + number
				);
	}

	private static String generateSpecialChar() {
		final String allowedSpecialChars = "!@#$%^&*";
		final int N = allowedSpecialChars.length();
		Random rd = new Random();
		int iLength = 1;
		StringBuilder specialChar = new StringBuilder(iLength);
		for (int i = 0; i < iLength; i++) {
			specialChar.append(allowedSpecialChars.charAt(rd.nextInt(N)));
		}
		
		
		return specialChar.toString();
	}

	/**
	 * It contains at least 8 characters and at most 20 characters.
	 * It contains at least one digit.
	 * It contains at least one upper case alphabet.
	 * It contains at least one lower case alphabet.
	 * It contains at least one special character which includes !@#$%&*()-+=^.
	 * It doesn’t contain any white space.
	 * regex = “^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8, 20}$”
	 * 
	 * Ref: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
	 * 
	 * @param password
	 * @return
	 */
	public static String getValidatedPassword(String password) {
		String thePassword = StringUtils.trimWhitespace(password);
		
		if(thePassword.isEmpty()) 
			return null;
		
		Pattern p = Pattern.compile(PASSWORD_REGEX);
		Matcher m = p.matcher(thePassword);
		if(m.matches())
			return thePassword;
		
		return null;
	}
	
}
