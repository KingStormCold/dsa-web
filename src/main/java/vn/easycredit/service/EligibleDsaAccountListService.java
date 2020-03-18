package vn.easycredit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.easycredit.converter.EligibleDsaAccountListConverter;
import vn.easycredit.dto.EligibleDsaAccountListDto;
import vn.easycredit.entity.EligibleDsaAccountListEntity;
import vn.easycredit.repository.EligibleDsaAccountListReponsitory;

@Service
public class EligibleDsaAccountListService {

	@Autowired
	private EligibleDsaAccountListConverter eligibleDsaAccountListConverter;
	
	@Autowired
	private EligibleDsaAccountListReponsitory eligibleDsaAccountListReponsitory;
	
	public List<EligibleDsaAccountListDto> findAllAccountActiveOrInactive(final String param) {
		List<EligibleDsaAccountListEntity> listActive = eligibleDsaAccountListReponsitory.findByStatus(param);
		List<EligibleDsaAccountListDto> result = new ArrayList<>();
		for (EligibleDsaAccountListEntity active: listActive) {
			result.add(eligibleDsaAccountListConverter.convertToDto(active));
		}
		return result;
	}
}
