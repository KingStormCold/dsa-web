package vn.easycredit.service;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.easycredit.common.RequestCommon;
import vn.easycredit.converter.DsaEligibleAccountHistoryConverter;
import vn.easycredit.dto.DsaEligibleAccountDto;
import vn.easycredit.dto.DsaEligibleAccountHistoryDto;
import vn.easycredit.repository.DsaEligibleAccountHistoryRepository;

@Service
@Transactional
public class DsaEligibleAccountHistoryService {
	
	@Autowired
	private DsaEligibleAccountHistoryRepository repository;
	
	@Autowired
	private DsaEligibleAccountHistoryConverter converter;
	
	public void sendMailCreatePasswordHitory(final DsaEligibleAccountDto accountDto, final boolean blockAccount) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setRequestMessage(blockAccount ? RequestCommon.BLOCK_ACCOUNT.getCode() : RequestCommon.SEND_MAIL_CREATE_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void checkCreatePassWordHitory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setRequestMessage(RequestCommon.CHECK_CREATE_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void craetedPasswordHitory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setExpireAccount(accountDto.getExpireAccount());
		dto.setRequestMessage(RequestCommon.CREATE_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void requestForgetPasswordHistory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setRequestMessage(RequestCommon.REQUEST_FORGET_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void checkForgetPasswordHistory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setRequestMessage(RequestCommon.CHECK_FORGET_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void forgetPasswordHistory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setExpireAccount(accountDto.getExpireAccount());
		dto.setRequestMessage(RequestCommon.FORGET_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
	
	public void resetPasswordHistory(final DsaEligibleAccountDto accountDto) {
		String id = UUID.randomUUID().toString();
		DsaEligibleAccountHistoryDto dto = new DsaEligibleAccountHistoryDto();
		dto.setHistoryId(id);
		dto.setCreatedDate(new Date(System.currentTimeMillis()));
		dto.setCurrentPassword(accountDto.getPassword());
		dto.setEmail(accountDto.getEmail());
		dto.setFullName(accountDto.getFullName());
		dto.setStatusImx(accountDto.getStatusImx());
		dto.setTokenCreatePassword(accountDto.getTokenCreatePassword());
		dto.setTokenForgotPassword(accountDto.getTokenForgotPassword());
		dto.setUserName(accountDto.getUserName());
		dto.setExpireAccount(accountDto.getExpireAccount());
		dto.setRequestMessage(RequestCommon.RESET_PASSWORD.getCode());
		repository.save(converter.convertToEntity(dto));
	}
}
