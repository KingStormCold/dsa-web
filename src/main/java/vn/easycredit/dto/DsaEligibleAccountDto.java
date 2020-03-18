package vn.easycredit.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleAccountDto {

	private String userName;

	private String userNameImx;

	private String password;

	private String fullName;

	private String email;

	private Date createdDate;

	private String tokenCreatePassword;

	private String tokenForgotPassword;

	private String statusImx;

	private Date updatedDate;

	private String dsaPartnerCode;

	private Date expireAccount;
	
	private Date expireResetPassword;
	
	private Date expireRequestForgotPassword;
	
	private Date expireLogin;
	
	private int countLoginWrong;

	private List<DsaEligibleAccountHistoryDto> dsaEligibleAccountLogs;
}
