package vn.easycredit.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dsa_eligible_login_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleLoginHistoryEntity {

	@Id
	@Column(name = "login_id")
	private String loginId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "created_login")
	private Date createdLogin;
	
	@Column(name = "created_logout")
	private Date createdLogout;
	
	@Column(name = "expire")
	private boolean expire;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loginHistory")
	@OrderBy("createdDate DESC")
	private List<DsaEligibleInquiryEntity> listDsaEligibleInquiry;
}
