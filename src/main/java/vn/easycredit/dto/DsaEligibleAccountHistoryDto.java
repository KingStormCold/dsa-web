package vn.easycredit.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleAccountHistoryDto {

	private String historyId;

	private String userName;

	private String currentPassword;

	private String fullName;

	private String email;

	private String tokenCreatePassword;

	private String tokenForgotPassword;

	private String statusImx;

	private Date createdDate;

	private Date expireAccount;
	
	private String requestMessage;
}
