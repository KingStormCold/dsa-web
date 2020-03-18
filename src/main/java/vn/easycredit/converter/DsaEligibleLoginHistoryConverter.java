package vn.easycredit.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.easycredit.dto.DsaEligibleLoginHistoryDto;
import vn.easycredit.entity.DsaEligibleLoginHistoryEntity;

@Component
public class DsaEligibleLoginHistoryConverter {

	@Autowired
	private ModelMapper mapper;
	
	public DsaEligibleLoginHistoryDto convertToDto(DsaEligibleLoginHistoryEntity entity) {
		if (StringUtils.isEmpty(entity)) {
			return null;
		}
		DsaEligibleLoginHistoryDto result = mapper.map(entity, DsaEligibleLoginHistoryDto.class);
		return result;
	}
	
	public DsaEligibleLoginHistoryEntity convertToEntity(DsaEligibleLoginHistoryDto dto) {
		DsaEligibleLoginHistoryEntity result = mapper.map(dto, DsaEligibleLoginHistoryEntity.class);
		return result;
	}
}
