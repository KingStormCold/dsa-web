package vn.easycredit.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "eligible-api")
@Data
public class EligibleApiSetting {
	
	private String accessTokenUrl;
	private String clientId;
	private String clientSecret;
	private String grantType;
	private String eligibleCheck;

}
