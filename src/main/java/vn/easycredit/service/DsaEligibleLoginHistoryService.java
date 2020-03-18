package vn.easycredit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.easycredit.converter.DsaEligibleLoginHistoryConverter;
import vn.easycredit.dto.DsaEligibleLoginHistoryDto;
import vn.easycredit.entity.DsaEligibleLoginHistoryEntity;
import vn.easycredit.repository.DsaEligibleLoginHistoryRepository;

@Service
public class DsaEligibleLoginHistoryService {
	
	@Autowired
	private DsaEligibleLoginHistoryConverter eligibleLoginHistoryConverter;
	
	@Autowired
	private DsaEligibleLoginHistoryRepository eligibleLoginHistoryRepository;
	
	public void save(DsaEligibleLoginHistoryDto loginHistoryDto) {
		eligibleLoginHistoryRepository.save(eligibleLoginHistoryConverter.convertToEntity(loginHistoryDto));
	}
	
	public DsaEligibleLoginHistoryDto findByLoginId(final String loginId) {
		Optional<DsaEligibleLoginHistoryEntity> entity  = eligibleLoginHistoryRepository.findById(loginId);
		return entity.isPresent() ? eligibleLoginHistoryConverter.convertToDto(entity.get()) : null;
	}
	
	public DsaEligibleLoginHistoryDto findByLoginIdAndUserName(final String loginId, final String userName) {
		DsaEligibleLoginHistoryEntity entity  = eligibleLoginHistoryRepository.findByLoginIdAndUserName(loginId, userName);
		return eligibleLoginHistoryConverter.convertToDto(entity);
	}
	
	public DsaEligibleLoginHistoryDto findByUserNameAndCreatedDate(final String userName) {
		DsaEligibleLoginHistoryEntity entity  = eligibleLoginHistoryRepository.findByUserName( userName);
		return eligibleLoginHistoryConverter.convertToDto(entity);
	}
}
