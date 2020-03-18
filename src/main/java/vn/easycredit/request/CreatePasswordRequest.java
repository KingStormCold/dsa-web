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
import vn.easycredit.response.RedirectPage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePasswordRequest {
	
	@JsonProperty("token_create_password")
	@SerializedName("token_create_password")
	private String tokenCreatePassword;
	
	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;

	@JsonProperty("password")
	@SerializedName("password")
	private String password;
	
	public RedirectPage validate() {
		PasswordValidator validator = new PasswordValidator();
		if (StringUtils.isEmpty(tokenCreatePassword) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY);
		} else if (!validator.validate(password)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.PASSWORD_INVALID);
		}
		return null;
	}
}
