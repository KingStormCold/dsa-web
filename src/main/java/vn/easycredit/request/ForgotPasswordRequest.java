package vn.easycredit.request;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.easycredit.common.Constant;
import vn.easycredit.common.HttpStatusCodeEnum;
import vn.easycredit.common.PasswordValidator;
import vn.easycredit.common.RedirectPageEnum;
import vn.easycredit.response.RedirectPage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
	
	@JsonProperty("token_forgot_password")
	@SerializedName("token_forgot_password")
	private String tokenForgotPassword;
	
	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;

	@JsonProperty("password")
	@SerializedName("password")
	private String password;

	public RedirectPage validate() {
		PasswordValidator validator = new PasswordValidator();
		if (StringUtils.isEmpty(tokenForgotPassword) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY, RedirectPageEnum.FORGOT.getCode(),  this.userName);
		} else if (!validator.validate(password)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.PASSWORD_INVALID, RedirectPageEnum.FORGOT.getCode(), this.userName);
		}
		return null;
	}
}
