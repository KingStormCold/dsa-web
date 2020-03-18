package vn.easycredit.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dsa_eligible_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleAccountEntity {

	@Id
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_name_imx", unique = true)
	private String userNameImx;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "token_create_password")
	private String tokenCreatePassword;
	
	@Column(name = "token_forgot_password")
	private String tokenForgotPassword;
	
	@Column(name = "status_imx")
	private String statusImx;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "dsa_partner_code")
	private String dsaPartnerCode;
	
	@Column(name = "expire_account")
	private Date expireAccount;
	
	@Column(name = "expire_reset_password")
	private Date expireResetPassword;
	
	@Column(name = "expire_request_forgot_password")
	private Date expireRequestForgotPassword;
	
	@Column(name = "expire_login")
	private Date expireLogin;
	
	@Column(name = "count_login_wrong")
	@ColumnDefault("0")
	private int countLoginWrong;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dsaEligibleAccount", cascade = CascadeType.PERSIST)
	@OrderBy("createdDate DESC")
	private List<DsaEligibleAccountHistoryEntity> dsaEligibleAccountLogs;
}
