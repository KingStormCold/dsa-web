package vn.easycredit.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
	
	@JsonProperty("result")
	@SerializedName("result")
	private String result;
	
	@JsonProperty("message")
	@SerializedName("message")
	private String message;

}
