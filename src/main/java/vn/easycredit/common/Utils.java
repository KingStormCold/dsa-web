package vn.easycredit.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Utils {
	
	public static final Pattern PATTERN_DATE = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)+$");
	public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
	
	public static final Pattern PATTERN_INTEGER = Pattern.compile("^[0-9]+$");
	public static final String PATTERN_TYPE_INTEGER = "int-type";
	
	public String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	public Date expireDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, 31);
		return currentDate.getTime();
	}
	
	public boolean passwordIsCorrect(final String password, final String encoderPassword) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, encoderPassword);
	}
	
	public boolean checkExpireDate(final Date expireDate) {
		Date today = new Date(System.currentTimeMillis());
		return today.after(expireDate);
	}
	
	public Date maintainTime(int time, boolean hour) {
		Calendar logoutTime = Calendar.getInstance();
		logoutTime.add(hour ? Calendar.HOUR : Calendar.MINUTE, time);
		return logoutTime.getTime();
	}
	
	public boolean checkExpire(final Date loginTimeExpire) {
		Date todayTime = new Date();
		return todayTime.getTime() >= loginTimeExpire.getTime();// true is right
	}
	
	public Date parseDate(String strDate) {
		try {
            if (PATTERN_DATE.matcher(strDate).matches()) {
            	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            	df.setTimeZone(UTC_TIME_ZONE);
            	df.setLenient(false);
            	return df.parse(strDate);
            }
		} catch (ParseException e) {
            log.info("Parse String to Date");
        }
		return null;
	}
	
	public boolean checkNationalIdMatch(final String identity) {
		Matcher matcher = PATTERN_INTEGER.matcher(identity);
		return matcher.matches();
	}
}
