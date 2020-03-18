package vn.easycredit.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!~`#$%^&*|/}<>?,.=';+_]).{8,12})";
	
	public PasswordValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}
	
	public boolean validate(final String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
