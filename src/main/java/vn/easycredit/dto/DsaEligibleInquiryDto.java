package vn.easycredit.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.easycredit.external.response.EligibleServiceCheckResponse;
import vn.easycredit.request.EnquiryEligibleRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleInquiryDto {

	private String inquiryId;

	private String createdBy;

	private Date createdDate;

	private Date resultTime;

	private String result;

	private String partnerCode;

	private String dsaAgentCode;

	private String nationalId;

	private String dateOfBirth;

	private String reason;

	private String loginId;
	
	private String requestId;

	public DsaEligibleInquiryDto(final DsaEligibleLoginHistoryDto historyDto,
			final DsaEligibleAccountDto dsaEligibleAccountDto, final EligibleServiceCheckResponse response,
			final EnquiryEligibleRequest request) {
		this.inquiryId = UUID.randomUUID().toString();
		this.createdBy = dsaEligibleAccountDto.getUserName();
		this.createdDate = new Date();
		this.resultTime = new Date();
		this.result = response.getCode();
		this.partnerCode = dsaEligibleAccountDto.getDsaPartnerCode().substring(1);
		this.dsaAgentCode = dsaEligibleAccountDto.getDsaPartnerCode();
		this.nationalId = request.getNationalId();
		this.dateOfBirth = request.getDateOfBirth();
		this.reason = response.getData().getReason();
		this.loginId = historyDto.getLoginId();
		this.requestId = response.getData().getRequestID();
	}
}