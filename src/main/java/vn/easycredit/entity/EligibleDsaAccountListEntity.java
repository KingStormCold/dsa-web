package vn.easycredit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "bi_logapi", name = "eligible_dsa_account_list")
public class EligibleDsaAccountListEntity {

	@Id
	@Column(name = "username")
	private String userName;
	
	@Column(name = "dsa_partner_code")
	private String dsaPartnerCode;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "status")
	private String status;
}
