package vn.easycredit.entity;

import java.util.Date;

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
@Table(name = "dsa_eligible_inquiry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DsaEligibleInquiryEntity {

	@Id
	@Column(name = "inquiry_id")
	private String inquiryId;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "result_time")
	private Date resultTime;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "partner_code")
	private String partnerCode;
	
	@Column(name = "dsa_agent_code")
	private String dsaAgentCode;
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "date_of_birth")
	private String dateOfBirth;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "login_id")
	private String loginId;
	
	@Column(name = "request_id")
	private String requestId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "login_id", insertable = false, updatable = false, nullable = false)
	private DsaEligibleLoginHistoryEntity loginHistory;
}
