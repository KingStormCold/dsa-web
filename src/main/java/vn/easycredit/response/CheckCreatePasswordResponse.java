package vn.easycredit.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCreatePasswordResponse extends ResultResponse{
	
	@JsonProperty("redirect_code")
	@SerializedName("redirect_code")
	private String redirectCode;
	
	@JsonProperty("user_name")
	@SerializedName("user_name")
	private String userName;
	
	public CheckCreatePasswordResponse(String result, String message, String redirectCode, String userName) {
		super(result, message);
		this.redirectCode = redirectCode;
		this.userName = userName;
	}

}
