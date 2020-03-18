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
public class ResetPasswordRequest {

	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;
	
	@JsonProperty("current_password")
	@SerializedName("current_password")
	private String currentPassword;
	
	@JsonProperty("new_password")
	@SerializedName("new_password")
	private String newPassword;
	
	public RedirectPage validate() {
		PasswordValidator validator = new PasswordValidator();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(currentPassword) || StringUtils.isEmpty(newPassword)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY);
		} else if (!validator.validate(currentPassword) || !validator.validate(newPassword)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.PASSWORD_INVALID);
		} else if (this.currentPassword.equals(this.newPassword)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.NOT_THE_SAME_PASSWORD);
		}
		return null;
	}
}
