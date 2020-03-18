package vn.easycredit.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "eligible-mail")
@Data
public class EligibleMailSetting {
	private String subjectMailOfCreatePassword;
	private String subjectMailOfForgetPassword;
	private String fromMail;
	private String linkMailCreate;
	private String linkMailForget;
}
