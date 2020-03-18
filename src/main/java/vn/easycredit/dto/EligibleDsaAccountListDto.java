package vn.easycredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleDsaAccountListDto {

	private String userName;

	private String dsaPartnerCode;

	private String fullName;

	private String email;

	private String status;
}
