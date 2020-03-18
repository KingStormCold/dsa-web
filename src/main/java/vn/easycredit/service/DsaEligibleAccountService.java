package vn.easycredit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.easycredit.converter.DsaEligibleAccountConverter;
import vn.easycredit.dto.DsaEligibleAccountDto;
import vn.easycredit.entity.DsaEligibleAccountEntity;
import vn.easycredit.repository.DsaEligibleAccountRepository;

@Service
@Transactional
public class DsaEligibleAccountService {
	
	@Autowired
	private DsaEligibleAccountConverter eligibleAccountConverter;
	
	@Autowired
	private DsaEligibleAccountRepository eligibleAccountRepository;
	
	public List<DsaEligibleAccountDto> findAll() {
		List<DsaEligibleAccountEntity> entities = eligibleAccountRepository.findAll();
		List<DsaEligibleAccountDto> result = new ArrayList<>();
		for (DsaEligibleAccountEntity entity : entities) {
			result.add(eligibleAccountConverter.convertToDto(entity));
		}
		return result;
	}
	
	public DsaEligibleAccountDto findByTokenCreatePassword(final String tokenCreatePassword) {
		List<DsaEligibleAccountEntity> entities = eligibleAccountRepository.findByTokenCreatePassword(tokenCreatePassword);
		if (!entities.isEmpty()) {
			return eligibleAccountConverter.convertToDto(entities.get(0));
		}
		return null;
	}
	
	public DsaEligibleAccountDto findByTokenForgotPassword(final String tokenForgotPassword) {
		List<DsaEligibleAccountEntity> entities = eligibleAccountRepository.findByTokenForgotPassword(tokenForgotPassword);
		if (!entities.isEmpty()) {
			return eligibleAccountConverter.convertToDto(entities.get(0));
		}
		return null;
	}
	
	public DsaEligibleAccountDto findByUserNameAndByTokenCreatePassword(final String userName, final String tokenCreatePassword) {
		List<DsaEligibleAccountEntity> entities = eligibleAccountRepository.findByUserNameByTokenCreatePassword(userName, tokenCreatePassword);
		if (!entities.isEmpty()) {
			return eligibleAccountConverter.convertToDto(entities.get(0));
		}
		return null;
	}
	
	public DsaEligibleAccountDto findByUserNameAndByTokenForgotPassword(final String userName, final String tokenForgotPassword) {
		List<DsaEligibleAccountEntity> entities = eligibleAccountRepository.findByUserNameByTokenForgotPassword(userName, tokenForgotPassword);
		if (!entities.isEmpty()) {
			return eligibleAccountConverter.convertToDto(entities.get(0));
		}
		return null;
	}
	
	public void save(DsaEligibleAccountDto dto) {
		eligibleAccountRepository.save(eligibleAccountConverter.convertToEntity(dto));
	}
	
	public DsaEligibleAccountDto findByUserName(final String userName) {
		Optional<DsaEligibleAccountEntity> eligibleAccount = eligibleAccountRepository.findById(userName);
		return eligibleAccount.isPresent() ? eligibleAccountConverter.convertToDto(eligibleAccount.get()) : null;
	}
}
