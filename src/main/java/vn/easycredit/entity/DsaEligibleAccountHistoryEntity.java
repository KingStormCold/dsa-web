package vn.easycredit.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dsa_eligible_account_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleAccountHistoryEntity {

	@Id
	@Column(name = "history_id")
	private String historyId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "current_password")
	private String currentPassword;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "token_create_password")
	private String tokenCreatePassword;
	
	@Column(name = "token_forgot_password")
	private String tokenForgotPassword;
	
	@Column(name = "status_imx")
	private String statusImx;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "expire_account")
	private Date expireAccount;
	
	@Column(name = "request_message")
	private String requestMessage;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_name", insertable = false, updatable = false, nullable = false)
	private DsaEligibleAccountEntity dsaEligibleAccount;
}
