package vn.easycredit.external.api;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.common.LogUtilities;
import vn.easycredit.external.request.EligibleServiceCheckRequest;
import vn.easycredit.external.response.AccessTokenResponse;
import vn.easycredit.external.response.EligibleServiceCheckResponse;
import vn.easycredit.setting.EligibleApiSetting;

@Component
@Slf4j
public class EligibleServiceCheckApi {

	@Autowired
	private EligibleApiSetting eligibleApiSetting;
	
	@Autowired
	private LogUtilities logUtilities;

	public EligibleServiceCheckResponse callApiElgibleCheck(EligibleServiceCheckRequest request) {
		log.info("Call API Check Eligible....");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Bearer " + getAccessToken(eligibleApiSetting.getClientId(), eligibleApiSetting.getClientSecret()));
			HttpEntity<EligibleServiceCheckRequest> entity = new HttpEntity<>(request, headers);
			RestTemplate restTemplate = new RestTemplate(getSSL());
			ResponseEntity<EligibleServiceCheckResponse> response = restTemplate.exchange(
					eligibleApiSetting.getEligibleCheck(), HttpMethod.POST, entity, EligibleServiceCheckResponse.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}
			return null;
		} catch (HttpStatusCodeException e) {
				String logJson = logUtilities.convertObjectToJsonLog(request);
				log.info(logJson);
				log.info("error call api..." + e.getResponseBodyAsString());
				return null;
		} catch (Exception e) {
			log.info("Connection time out from 3rd...");
			log.info(e.toString());
			return null;
		}
	}

	private String encodedClientInfo(String clientId, String clientSecret) {
		String clientInfo = clientId + ":" + clientSecret;
		final byte[] encodedClientInfo = Base64.encodeBase64(clientInfo.getBytes());
		return new String(encodedClientInfo);
	}

	private HttpHeaders getHttpHeadersAccessToken(String clientId, String clientSecret) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		headers.add("Authorization", "Basic " + encodedClientInfo(clientId, clientSecret));
		return headers;
	}

	public String getAccessToken(String clientId, String clientSecret) {
		log.info("InternalApi getAccessToken");
		if (StringUtils.isEmpty(eligibleApiSetting) || StringUtils.isEmpty(eligibleApiSetting.getAccessTokenUrl())) {
			log.info("InternalApi getAccessToken : token url missing from config");
			return null;
		}
		String grantType = "grant_type=" + eligibleApiSetting.getGrantType();
		try {
			HttpEntity<Object> httpEntity = new HttpEntity<>(grantType,
					getHttpHeadersAccessToken(clientId, clientSecret));
			RestTemplate restTemplate = new RestTemplate(getSSL());
			ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(eligibleApiSetting.getAccessTokenUrl(),
					HttpMethod.POST, httpEntity, AccessTokenResponse.class);
			if (StringUtils.isEmpty(response) || response.getStatusCode() != HttpStatus.OK) {
				return null;
			}
			if (response.getBody() != null) {
				return response.getBody().getAccessToken();
			} else {
				return null;
			}

		} catch (Exception e) {
			log.info("InternalApi->getAccessToken Error: " + e.toString(), e);
			return null;
		}
	}

	private HttpComponentsClientHttpRequestFactory getSSL()	throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		requestFactory.setReadTimeout(60 * 1000);
		requestFactory.setConnectTimeout(60 * 1000);
		return requestFactory;
	}
}
