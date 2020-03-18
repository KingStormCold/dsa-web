package vn.easycredit.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.easycredit.dto.DsaEligibleAccountHistoryDto;
import vn.easycredit.entity.DsaEligibleAccountHistoryEntity;

@Component
public class DsaEligibleAccountHistoryConverter {

	@Autowired
	private ModelMapper mapper;
	
	public DsaEligibleAccountHistoryDto convertToDto(DsaEligibleAccountHistoryEntity entity) {
		if (StringUtils.isEmpty(entity)) {
			return null;
		}
		DsaEligibleAccountHistoryDto result = mapper.map(entity, DsaEligibleAccountHistoryDto.class);
		return result;
	}
	
	public DsaEligibleAccountHistoryEntity convertToEntity(DsaEligibleAccountHistoryDto dto) {
		DsaEligibleAccountHistoryEntity result = mapper.map(dto, DsaEligibleAccountHistoryEntity.class);
		return result;
	}
}
