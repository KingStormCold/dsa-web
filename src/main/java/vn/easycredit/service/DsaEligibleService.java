package vn.easycredit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.common.Constant;
import vn.easycredit.common.HttpStatusCodeEnum;
import vn.easycredit.common.RedirectPageEnum;
import vn.easycredit.common.RequestCommon;
import vn.easycredit.common.Utils;
import vn.easycredit.config.ScheduleConfig;
import vn.easycredit.converter.DsaEligibleAccountConverter;
import vn.easycredit.dto.DsaEligibleAccountDto;
import vn.easycredit.dto.DsaEligibleInquiryDto;
import vn.easycredit.dto.DsaEligibleLoginHistoryDto;
import vn.easycredit.dto.EligibleDsaAccountListDto;
import vn.easycredit.external.api.EligibleServiceCheckApi;
import vn.easycredit.external.request.EligibleServiceCheckRequest;
import vn.easycredit.external.response.EligibleServiceCheckResponse;
import vn.easycredit.repository.DsaEligibleAccountRepository;
import vn.easycredit.request.CreatePasswordRequest;
import vn.easycredit.request.EnquiryEligibleRequest;
import vn.easycredit.request.ForgotPasswordRequest;
import vn.easycredit.request.LoginRequest;
import vn.easycredit.request.ResetPasswordRequest;
import vn.easycredit.response.InquiryResponse;
import vn.easycredit.response.RedirectPage;

@Service
@Transactional
@Slf4j
public class DsaEligibleService {
	
	@Autowired
	private DsaEligibleAccountService eligibleAccountService;
	
	@Autowired
	private DsaEligibleAccountHistoryService eligibleAccountHistoryService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private EligibleDsaAccountListService eligibleDsaAccountListService;
	
	@Autowired
	private DsaEligibleAccountConverter dsaEligibleAccountConverter;
	
	@Autowired
	private DsaEligibleAccountRepository dsaEligibleAccountRepository;
	
	@Autowired
	private DsaEligibleLoginHistoryService eligibleLoginHistoryService;
	
	@Autowired
	private ScheduleConfig scheduleConfig;
	
	@Autowired
	private EligibleServiceCheckApi eligibleServiceCheckApi;
	
	@Autowired
	private DsaEligibleInquiryService eligibleInquiryService;
	
	public synchronized RedirectPage login(final LoginRequest request) {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserName(request.getUserName());
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			log.info("login null...");
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.PASSWORD_WRONG, RedirectPageEnum.LOGIN.getCode());
		} else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			log.info("login Inactive..." + dsaEligibleAccountDto.getStatusImx());
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.PASSWORD_WRONG, RedirectPageEnum.LOGIN.getCode());
		} else if(!utils.passwordIsCorrect(request.getPassWord(), dsaEligibleAccountDto.getPassword())) {
			log.info("password wrong..." + dsaEligibleAccountDto.getCountLoginWrong());
			if (!StringUtils.isEmpty(dsaEligibleAccountDto.getExpireLogin()) && !utils.checkExpire(dsaEligibleAccountDto.getExpireLogin())) {
				return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ERROR_EXPIRE_LOGIN, RedirectPageEnum.LOGIN.getCode());
			}
			int countLogin = dsaEligibleAccountDto.getCountLoginWrong();
			dsaEligibleAccountDto.setCountLoginWrong(countLogin + 1);
			if (dsaEligibleAccountDto.getCountLoginWrong() == 5) {
				dsaEligibleAccountDto.setCountLoginWrong(0);
				dsaEligibleAccountDto.setExpireLogin(utils.maintainTime(15, false));
			}
			eligibleAccountService.save(dsaEligibleAccountDto);
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.PASSWORD_WRONG, RedirectPageEnum.LOGIN.getCode());
		} else if(utils.checkExpireDate(dsaEligibleAccountDto.getExpireAccount())) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.RESET.getCode(), dsaEligibleAccountDto.getUserName());
		} else if (!StringUtils.isEmpty(dsaEligibleAccountDto.getExpireLogin()) && !utils.checkExpire(dsaEligibleAccountDto.getExpireLogin())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ERROR_EXPIRE_LOGIN, RedirectPageEnum.LOGIN.getCode());
		}
		DsaEligibleLoginHistoryDto loginHistoryDto = eligibleLoginHistoryService.findByUserNameAndCreatedDate(request.getUserName());
		if (!StringUtils.isEmpty(loginHistoryDto) && !loginHistoryDto.isExpire()) {
			log.info("set expire login...");
			if (utils.checkExpire(loginHistoryDto.getCreatedLogout())) {
				//if createdLogout time out, create new login_inquiry
				loginHistoryDto.setExpire(true);
				eligibleLoginHistoryService.save(loginHistoryDto);
			} else {
				// if createdLogout not time out, re-use
				log.info("re-use..." +loginHistoryDto.getLoginId());
				dsaEligibleAccountDto.setCountLoginWrong(0);
				eligibleAccountService.save(dsaEligibleAccountDto);
				scheduleConfig.mapLoginHistory.put(loginHistoryDto.getLoginId(), loginHistoryDto);
				return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.INQUIRY.getCode(), dsaEligibleAccountDto.getUserName(), loginHistoryDto.getLoginId());
			}
		}
		log.info("continue...");
		String logId = UUID.randomUUID().toString();
		DsaEligibleLoginHistoryDto loginHistory = new DsaEligibleLoginHistoryDto();
		loginHistory.setLoginId(logId);
		loginHistory.setCreatedLogin(new Date());
		loginHistory.setCreatedLogout(utils.maintainTime(1, true));
		loginHistory.setExpire(false);
		loginHistory.setUserName(request.getUserName());
		eligibleLoginHistoryService.save(loginHistory);
		dsaEligibleAccountDto.setCountLoginWrong(0);
		eligibleAccountService.save(dsaEligibleAccountDto);
		scheduleConfig.mapLoginHistory.put(logId, loginHistory);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.INQUIRY.getCode(), dsaEligibleAccountDto.getUserName(), logId);
	}
	
	public void createAccountDSA() {
		try {
			log.info("get all account active in IMX...");
			List<EligibleDsaAccountListDto> listActive = eligibleDsaAccountListService.findAllAccountActiveOrInactive(Constant.IMX_ACTIVE);
			List<DsaEligibleAccountDto> eligibleAccountDtos = eligibleAccountService.findAll();
			Map<String, DsaEligibleAccountDto> mapAccount = new HashMap<>();
			for (DsaEligibleAccountDto accountDto : eligibleAccountDtos) {
				mapAccount.put(accountDto.getUserName(), accountDto);
			}
			for (EligibleDsaAccountListDto accountInImx : listActive) {
				DsaEligibleAccountDto accountInfo = mapAccount.get(accountInImx.getUserName());
				String tokenCreate = UUID.randomUUID().toString();
				if (StringUtils.isEmpty(accountInfo)) {
					log.info("create account for dsa..." + accountInImx);
					DsaEligibleAccountDto createAccount = blockOrCreateAccount(accountInImx, tokenCreate, false, accountInfo);
					mailService.sendMail(createAccount.getEmail(), tokenCreate, createAccount.getFullName(), false);
				} else {
					//check account from Imx with status == Inactive and send mail
					if (!accountInfo.getStatusImx().equals(accountInImx.getStatus())) {
						log.info("create account from Inactive to Active..." + accountInImx);
						DsaEligibleAccountDto createAccount = blockOrCreateAccount(accountInImx, tokenCreate, false, accountInfo);
						mailService.sendMail(createAccount.getEmail(), tokenCreate, createAccount.getFullName(), false);
					}
				}
			}
			log.info("get all account active in IMX... DONE");
		} catch (Exception e) {
			log.info("get all account active Exception" + e.toString());
		}
	}
	
	public void checkStatusAndBlockAccount() {
		try {
			log.info("get all account inactive in IMX");
			List<EligibleDsaAccountListDto> listInactive = eligibleDsaAccountListService.findAllAccountActiveOrInactive(Constant.IMX_INACTIVE);
			List<DsaEligibleAccountDto> eligibleAccountDtos = eligibleAccountService.findAll();
			Map<String, DsaEligibleAccountDto> mapAccount = new HashMap<>();
			for (DsaEligibleAccountDto accountDto : eligibleAccountDtos) {
				mapAccount.put(accountDto.getUserName(), accountDto);
			}
			for (EligibleDsaAccountListDto accountInImx : listInactive) {
				DsaEligibleAccountDto accountInfo = mapAccount.get(accountInImx.getUserName());
				//check account from Imx with status == Inactive and block account
				if (!StringUtils.isEmpty(accountInfo) && !accountInfo.getStatusImx().equals(accountInImx.getStatus())) {
					blockOrCreateAccount(accountInImx, "", true, accountInfo);
				}
			}
			log.info("get all account inactive in IMX...DONE");
		} catch (Exception e) {
			log.info("get all account inactive Exception" + e.toString());
		}
	}

	private DsaEligibleAccountDto blockOrCreateAccount(final EligibleDsaAccountListDto accountInImx,
			final String tokenCreate, final boolean blockAccount, DsaEligibleAccountDto accountInfo) {
		DsaEligibleAccountDto createAccount = new DsaEligibleAccountDto();
		createAccount.setUserName(accountInImx.getUserName());
		createAccount.setCreatedDate(StringUtils.isEmpty(accountInfo) ? new Date(System.currentTimeMillis()) : accountInfo.getCreatedDate());
		createAccount.setDsaPartnerCode(accountInImx.getDsaPartnerCode());
		createAccount.setEmail(accountInImx.getEmail());
		createAccount.setFullName(accountInImx.getFullName());
		createAccount.setStatusImx(accountInImx.getStatus());
		createAccount.setTokenCreatePassword(blockAccount ? "" : tokenCreate);
		createAccount.setTokenForgotPassword(blockAccount ? "" : UUID.randomUUID().toString());
		createAccount.setUpdatedDate(new Date(System.currentTimeMillis()));
		createAccount.setUserNameImx(accountInImx.getUserName());
		dsaEligibleAccountRepository.save(dsaEligibleAccountConverter.convertToEntity(createAccount));
		eligibleAccountHistoryService.sendMailCreatePasswordHitory(createAccount, blockAccount);
		return createAccount;
	}
	
	public RedirectPage checkCreatePassword(final String tokenCreatePassword) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByTokenCreatePassword(tokenCreatePassword);
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.LOGIN.getCode(), "");
		} else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.ACCOUNT_INACTIVE, RedirectPageEnum.LOGIN.getCode(), "");
		} else if (StringUtils.isEmpty(dsaEligibleAccountDto.getPassword())) {
			eligibleAccountHistoryService.checkCreatePassWordHitory(dsaEligibleAccountDto);
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.CREATE.getCode(), dsaEligibleAccountDto.getUserName());
		} else {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.LOGIN.getCode(), "");
		}
	}
	
	public synchronized RedirectPage createPassword(final CreatePasswordRequest request) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserNameAndByTokenCreatePassword(request.getUserName(), request.getTokenCreatePassword());
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.CREATE_PASSWORD_ERROR, RedirectPageEnum.LOGIN.getCode());
		}
		if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ACCOUNT_INACTIVE, RedirectPageEnum.LOGIN.getCode());
		} else if (!StringUtils.isEmpty(dsaEligibleAccountDto.getPassword())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.CREATE_PASSWORD_ERROR, RedirectPageEnum.LOGIN.getCode());
		}
		
		dsaEligibleAccountDto.setPassword(utils.encodePassword(request.getPassword()));
		dsaEligibleAccountDto.setExpireAccount(utils.expireDate());
		dsaEligibleAccountDto.setUpdatedDate(new Date(System.currentTimeMillis()));
		
		eligibleAccountService.save(dsaEligibleAccountDto);
		//add log account history
		eligibleAccountHistoryService.craetedPasswordHitory(dsaEligibleAccountDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.CREATE_PASSWORD_SUCCESS, RedirectPageEnum.LOGIN.getCode());
	}
	
	public RedirectPage requestForgotPassword(final String userName) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserName(userName);
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.NOT_FOUND_USER_ERROR);
		} else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ACCOUNT_INACTIVE);
		} else if (!StringUtils.isEmpty(dsaEligibleAccountDto.getExpireRequestForgotPassword()) 
				&& !utils.checkExpire(dsaEligibleAccountDto.getExpireRequestForgotPassword())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ERROR_EXPIRE_REQUEST_FORGOT_PASSWORD);
		}
		
		String tokenForgetPassword = dsaEligibleAccountDto.getTokenForgotPassword();
		String email = dsaEligibleAccountDto.getEmail();
		String fullName = dsaEligibleAccountDto.getFullName();
		dsaEligibleAccountDto.setUpdatedDate(new Date(System.currentTimeMillis()));
		dsaEligibleAccountDto.setExpireResetPassword(utils.maintainTime(1, true));
		dsaEligibleAccountDto.setExpireRequestForgotPassword(utils.maintainTime(1, true));
		eligibleAccountService.save(dsaEligibleAccountDto);
		mailService.sendMail(email, tokenForgetPassword, fullName, true);
		eligibleAccountHistoryService.requestForgetPasswordHistory(dsaEligibleAccountDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText());
	}
	
	public RedirectPage checkForgotPassword(final String tokenForgotPassword) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByTokenForgotPassword(tokenForgotPassword);
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.LOGIN.getCode(), "");
		}else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.ACCOUNT_INACTIVE, RedirectPageEnum.LOGIN.getCode(), "");
		} else if (utils.checkExpire(dsaEligibleAccountDto.getExpireResetPassword())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.CONNECTION_TIME_OUT, RedirectPageEnum.LOGIN.getCode());
		}
		eligibleAccountHistoryService.checkForgetPasswordHistory(dsaEligibleAccountDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), RedirectPageEnum.FORGOT.getCode(), dsaEligibleAccountDto.getUserName());
	}
	
	public synchronized RedirectPage forgotPassword(final ForgotPasswordRequest request) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserNameAndByTokenForgotPassword(request.getUserName(), request.getTokenForgotPassword());
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.CREATE_PASSWORD_ERROR, RedirectPageEnum.LOGIN.getCode(), request.getUserName());
		} else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ACCOUNT_INACTIVE, RedirectPageEnum.LOGIN.getCode(), request.getUserName());
		} else if (utils.passwordIsCorrect(request.getPassword(), dsaEligibleAccountDto.getPassword())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.NOT_THE_SAME_CURRENT_PASSWORD, RedirectPageEnum.LOGIN.getCode(), request.getUserName());
		}
		
		dsaEligibleAccountDto.setPassword(utils.encodePassword(request.getPassword()));
		dsaEligibleAccountDto.setExpireAccount(utils.expireDate());
		dsaEligibleAccountDto.setUpdatedDate(new Date(System.currentTimeMillis()));
		dsaEligibleAccountDto.setTokenForgotPassword(UUID.randomUUID().toString());
		
		eligibleAccountService.save(dsaEligibleAccountDto);
		eligibleAccountHistoryService.forgetPasswordHistory(dsaEligibleAccountDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.CREATE_PASSWORD_SUCCESS, RedirectPageEnum.LOGIN.getCode());
	}
	
	public synchronized RedirectPage resetPassword(final ResetPasswordRequest request) throws Exception {
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserName(request.getUserName());
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.NOT_FOUND_USER_ERROR, RedirectPageEnum.LOGIN.getCode());
		} else if (!utils.passwordIsCorrect(request.getCurrentPassword(), dsaEligibleAccountDto.getPassword())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ERROR_CURRENT_PASSWORD, RedirectPageEnum.RESET.getCode());
		} else if (Constant.IMX_INACTIVE.equals(dsaEligibleAccountDto.getStatusImx())) {
			return new RedirectPage(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.ACCOUNT_INACTIVE, RedirectPageEnum.LOGIN.getCode());
		}
		dsaEligibleAccountDto.setPassword(utils.encodePassword(request.getNewPassword()));
		dsaEligibleAccountDto.setExpireAccount(utils.expireDate());
		dsaEligibleAccountDto.setUpdatedDate(new Date(System.currentTimeMillis()));
		eligibleAccountService.save(dsaEligibleAccountDto);
		eligibleAccountHistoryService.resetPasswordHistory(dsaEligibleAccountDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.CREATE_PASSWORD_SUCCESS, RedirectPageEnum.LOGIN.getCode());
	}
	
	public boolean checkLoginHistory(DsaEligibleLoginHistoryDto loginHistoryDto) {
		DsaEligibleLoginHistoryDto historyDto = eligibleLoginHistoryService.findByLoginId(loginHistoryDto.getLoginId());
		if (StringUtils.isEmpty(historyDto) || historyDto.isExpire()) {
			return true;
		} else if (utils.checkExpire(historyDto.getCreatedLogout()) && !historyDto.isExpire()) {
			historyDto.setExpire(true);
			eligibleLoginHistoryService.save(historyDto);
			return true;
		}
		return false;
	}
	
	public synchronized RedirectPage logout(final String userName, final String loginId) {
		DsaEligibleLoginHistoryDto historyDto = eligibleLoginHistoryService.findByLoginIdAndUserName(loginId, userName);
		if (StringUtils.isEmpty(historyDto)) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), Constant.NOT_FOUND, RedirectPageEnum.LOGIN.getCode()); 
		} else if (historyDto.isExpire()) {
			return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), "", RedirectPageEnum.LOGIN.getCode()); 
		}
		historyDto.setExpire(true);
		historyDto.setCreatedLogout(new Date(System.currentTimeMillis()));
		eligibleLoginHistoryService.save(historyDto);
		return new RedirectPage(HttpStatusCodeEnum.SUCCESS.getCode(), "", RedirectPageEnum.LOGIN.getCode()); 
	}
	
	public synchronized InquiryResponse inquiry(final EnquiryEligibleRequest request) throws Exception{
		DsaEligibleLoginHistoryDto historyDto = eligibleLoginHistoryService.findByLoginIdAndUserName(request.getLoginId(), request.getUserName());
		if (StringUtils.isEmpty(historyDto)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.NOT_FOUND, "", RedirectPageEnum.LOGIN.getCode()); 
		} else if (historyDto.isExpire()) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.CONNECTION_TIME_OUT, "", RedirectPageEnum.LOGIN.getCode()); 
		}
		DsaEligibleAccountDto dsaEligibleAccountDto = eligibleAccountService.findByUserName(request.getUserName());
		if (StringUtils.isEmpty(dsaEligibleAccountDto)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), Constant.NOT_FOUND_USER_ERROR, "", RedirectPageEnum.LOGIN.getCode());
		}
		EligibleServiceCheckRequest serviceCheckRequest = new EligibleServiceCheckRequest(request, dsaEligibleAccountDto);
		EligibleServiceCheckResponse response = eligibleServiceCheckApi.callApiElgibleCheck(serviceCheckRequest);
		if (StringUtils.isEmpty(response)) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.INQUIRY.getCode());
		}
		DsaEligibleInquiryDto eligibleInquiryDto;
		try {
			eligibleInquiryDto = new DsaEligibleInquiryDto(historyDto, dsaEligibleAccountDto, response, request);
			eligibleInquiryService.save(eligibleInquiryDto);
		} catch (PersistenceException e) {
			return new InquiryResponse(HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getCode(), RequestCommon.NOT_ELIGIBLE.getText(), HttpStatusCodeEnum.ERROR_INTERNAL_SERVER.getText(), RedirectPageEnum.INQUIRY.getCode());
		}
		String message = Constant.NOT_ELIGIBLE.equals(eligibleInquiryDto.getResult()) ? RequestCommon.NOT_ELIGIBLE.getText() : RequestCommon.ELIGIBLE.getText();
		return new InquiryResponse(HttpStatusCodeEnum.SUCCESS.getCode(), HttpStatusCodeEnum.SUCCESS.getText(), message, RedirectPageEnum.INQUIRY.getCode());
	}
}
