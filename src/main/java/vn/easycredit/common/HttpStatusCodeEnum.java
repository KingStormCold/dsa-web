package vn.easycredit.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpStatusCodeEnum {
	ERROR_INTERNAL_SERVER("500","Internal Server Error"), 
	ERROR_BAD_REQUSET("400", "Bad request"), 
	SUCCESS("200", "Success");
	
	private final String code;
	
	private final String text;

}
