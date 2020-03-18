package vn.easycredit.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InquiryResponse extends ResultResponse{

	@JsonProperty("result_enquiry")
	@SerializedName("result_enquiry")
	private String resultEnquiry;
	
	@JsonProperty("redirect_page")
	@SerializedName("redirect_page")
	private String redirectPage;
	
	public InquiryResponse(String result, String message) {
		super(result, message);
	}
	
	public InquiryResponse(String result, String message, String resultEnquiry) {
		super(result, message);
		this.resultEnquiry = resultEnquiry;
	}
	
	public InquiryResponse(String result, String message, String resultEnquiry, String redirectPage) {
		super(result, message);
		this.resultEnquiry = resultEnquiry;
		this.redirectPage = redirectPage;
	}
}
