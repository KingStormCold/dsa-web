package vn.easycredit.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedirectPage extends ResultResponse {

	@JsonProperty("redirect_code")
	@SerializedName("redirect_code")
	private String redirectCode;
	
	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;
	
	@JsonProperty("login_id")
	@SerializedName("login_id")
	private String loginId;
	
	public RedirectPage(String result, String message) {
		super(result, message);
	}
	
	public RedirectPage(String result, String message, String redirectCode) {
		super(result, message);
		this.redirectCode = redirectCode;
	}
	
	public RedirectPage(String result, String message, String redirectCode, String userName) {
		super(result, message);
		this.redirectCode = redirectCode;
		this.userName = userName;
	}
	
	public RedirectPage(String result, String message, String redirectCode, String userName, String loginId) {
		super(result, message);
		this.redirectCode = redirectCode;
		this.userName = userName;
		this.loginId = loginId;
	}
}
