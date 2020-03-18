package vn.easycredit.external.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleServiceCheckResponse {

	@SerializedName("code")
	@JsonProperty("code")
	private String code;
	
	@SerializedName("message")
	@JsonProperty("message")
	private String message;
	
	@SerializedName("data")
	@JsonProperty("data")
	@JsonInclude(Include.NON_EMPTY)
	private Data data;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Data {
		
		@SerializedName("request_id")
		@JsonProperty("request_id")
		private String requestID;
		
		@SerializedName("channel")
		@JsonProperty("channel")
		private String channel;
		
		@SerializedName("reason")
		@JsonProperty("reason")
		@JsonInclude(Include.NON_EMPTY)
		private String reason;
	}
}
