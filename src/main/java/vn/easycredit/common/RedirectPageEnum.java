package vn.easycredit.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedirectPageEnum {
	LOGIN("00", "Redirect to login page."),
	CREATE("01", "Redirect to create password page."),
	RESET("02", "Redirect to reset password page."),
	FORGOT("03", "Redirect to forget password page."),
	INQUIRY("04", "Redirect to inquiry eligible page.");
	
	private final String code;
	
	private final String text;

}
