package vn.easycredit.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleLoginHistoryDto {

	private String loginId;

	private String userName;

	private Date createdLogin;

	private Date createdLogout;

	private boolean expire;

	private List<DsaEligibleInquiryDto> listDsaEligibleInquiry;
}
