package vn.easycredit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.common.Constant;
import vn.easycredit.common.HttpStatusCodeEnum;
import vn.easycredit.common.LogUtilities;
import vn.easycredit.common.RedirectPageEnum;
import vn.easycredit.request.CreatePasswordRequest;
import vn.easycredit.request.EnquiryEligibleRequest;
import vn.easycredit.request.ForgotPasswordRequest;
import vn.easycredit.request.LoginRequest;
import vn.easycredit.request.ResetPasswordRequest;
import vn.easycredit.response.InquiryResponse;
import vn.easycredit.response.RedirectPage;
import vn.easycredit.service.DsaEligibleService;

@RestController
@RefreshScope
@Slf4j
public class DsaAuthController {
	
	@Autowired
	private DsaEligibleService dsaEligibleService;
	
	@Autowired
	private LogUtilities logUtilities;
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@PostMapping(value = "/v1/login")
	public RedirectPage login(@RequestBody LoginRequest request) {
		String jsonLog = logUtilities.convertObjectToJsonLog(request);
		log.info("login..." + jsonLog);
		try {
			if (StringUtils.isEmpty(request.validate())) {
				return dsaEligibleService.login(request);
			}
			return request.validate();
		} catch (Exception e) {
			log.info(e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText());
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@PostMapping(value = "/v1/enquiry-eligible")
	public InquiryResponse enquiryEligible(@RequestBody EnquiryEligibleRequest request) {
		String jsonLog = logUtilities.convertObjectToJsonLog(request);
		log.info("inquiry.... "+ jsonLog);
		try {
			if (StringUtils.isEmpty(request.validate())) {
				return dsaEligibleService.inquiry(request);
			}
			return request.validate();
		} catch (Exception e) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), "", RedirectPageEnum.LOGIN.getCode());
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@GetMapping(value = "/v1/logout")
	public RedirectPage logout(@RequestParam("user_name") String userName, @RequestParam("login_id") String loginId) {
		String jsonLog = logUtilities.convertObjectToJsonLog(userName + loginId);
		log.info("logout.... "+ jsonLog);
		return dsaEligibleService.logout(userName, loginId);
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@GetMapping(value = "/v1/check-create-password")
	public RedirectPage checkCreatePassword(@RequestParam("token_create") String tokenCreatePassword) {
		log.info("Check create password with " + tokenCreatePassword);
		try {
			return dsaEligibleService.checkCreatePassword(tokenCreatePassword);
		} catch (Exception e) {
			log.info("error when check create password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.LOGIN.getCode(), "");
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@PostMapping(value = "/v1/create-password")
	public RedirectPage createPassword(@RequestBody CreatePasswordRequest request) {
		String json = logUtilities.convertObjectToJsonLog(request);
		log.info("create password with..." + json);
		try {
			if (StringUtils.isEmpty(request.validate())) {
				return dsaEligibleService.createPassword(request);
			}
			return request.validate();
		} catch (Exception e) {
			log.info("error when create password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.LOGIN.getCode());
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@GetMapping(value = "/v1/request-forgot-password")
	public RedirectPage requestForgotPassword(@RequestParam("user_name") String userName) {
		log.info("request forget password with... " + userName);
		try {
			if (StringUtils.isEmpty(userName)) {
				return new RedirectPage(HttpStatusCodeEnum.ERROR_BAD_REQUSET.getCode(), Constant.INPUT_EMPTY);
			}
			return dsaEligibleService.requestForgotPassword(userName);
		} catch (Exception e) {
			log.info("error when request forget password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText());
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@GetMapping(value = "/v1/check-forgot-password")
	public RedirectPage checkForgotPassword(@RequestParam("token_forgot") String tokenForgotPassword) {
		log.info("Check forget password with... " + tokenForgotPassword);
		try {
			return dsaEligibleService.checkForgotPassword(tokenForgotPassword);
		} catch (Exception e) {
			log.info("error when check forget password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.LOGIN.getCode(), "");
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@PostMapping(value = "/v1/forgot-password")
	public RedirectPage forgotPassword(@RequestBody ForgotPasswordRequest request) {
		String json = logUtilities.convertObjectToJsonLog(request);
		log.info("forget password with... " + json);
		try {
			if (StringUtils.isEmpty(request.validate())) {
				return dsaEligibleService.forgotPassword(request);
			}
			return request.validate();
		} catch (Exception e) {
			log.info("error when forget password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.LOGIN.getCode(), request.getUserName());
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('eligible_dsa_web')")
	@PostMapping(value = "/v1/reset-password")
	public RedirectPage resetPassword(@RequestBody ResetPasswordRequest request) {
		String json = logUtilities.convertObjectToJsonLog(request);
		log.info("reset password with... " + json);
		try {
			if (StringUtils.isEmpty(request.validate())) {
				return dsaEligibleService.resetPassword(request);
			}
			return request.validate();
		} catch (Exception e) {
			log.info("error when reset password" + e.toString());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.LOGIN.getCode());
		}
	}
}
