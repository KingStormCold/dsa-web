package vn.easycredit.request;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.easycredit.common.Constant;
import vn.easycredit.common.HttpStatusCodeEnum;
import vn.easycredit.response.RedirectPage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;
	
	@JsonProperty("pass_word")
	@SerializedName("pass_word")
	private String passWord;
	
	public RedirectPage validate() {
		if (StringUtils.isEmpty(this.userName) || StringUtils.isEmpty(this.passWord)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY);
		}
		return null;
	}
}
