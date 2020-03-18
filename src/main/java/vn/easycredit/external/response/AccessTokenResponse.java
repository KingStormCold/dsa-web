package vn.easycredit.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {

	@JsonProperty("access_token")
	@SerializedName("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	@SerializedName("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	@SerializedName("expires_in")
	private String expiresIn;
	
	@JsonProperty("scope")
	@SerializedName("scope")
	private String scope;
}
