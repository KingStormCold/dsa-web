package vn.easycredit.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestCommon {
	SEND_MAIL_CREATE_PASSWORD("CP-001", "Send mail to create password"),
	CHECK_CREATE_PASSWORD("CP-002", "Check create password"),
	CREATE_PASSWORD("CP-003", "Create password"),
	SEND_MAIL_FORGET_PASSWORD("FP-001", "Send mail to forget password"),
	REQUEST_FORGET_PASSWORD("FP-002", "Request to forget password"),
	CHECK_FORGET_PASSWORD("FP-003", "Check forget password"),
	FORGET_PASSWORD("FP-004", "Forget password"),
	RESET_PASSWORD("RP-001", "Reset password"),
	BLOCK_ACCOUNT("BA-001", "Block account"),
	ELIGIBLE("OK-000", "Congratulation! Your customer is eligible for a new loan in Easy Credit."),
	NOT_ELIGIBLE("NO-000", "Sorry, your customer is not eligible for a new loan in Easy Credit."),
	;

	private final String code;
	
	private final String text;
}
