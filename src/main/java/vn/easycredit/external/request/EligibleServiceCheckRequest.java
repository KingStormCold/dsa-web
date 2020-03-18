package vn.easycredit.external.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.easycredit.dto.DsaEligibleAccountDto;
import vn.easycredit.request.EnquiryEligibleRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleServiceCheckRequest {
	
	@JsonProperty("request_id")
	@SerializedName("request_id")
	private String requestId;
	
	@JsonProperty("channel")
	@SerializedName("channel")
	private String channel;
	
	@JsonProperty("partner_code")
	@SerializedName("partner_code")
	private String partnerCode;
	
	@JsonProperty("identity_card_id")
	@SerializedName("identity_card_id")
	private String identityCardId;
	
	@JsonProperty("date_of_birth")
	@SerializedName("date_of_birth")
	private String dateOfBirth;
	
	public EligibleServiceCheckRequest (EnquiryEligibleRequest request, DsaEligibleAccountDto account) {
		this.requestId = "DSAWEB" + System.currentTimeMillis();
		this.channel = "DSA_WEB";
		this.partnerCode = account.getDsaPartnerCode().substring(1);
		this.identityCardId = request.getNationalId();
		this.dateOfBirth = request.getDateOfBirth();
	}
}
