package vn.easycredit.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.easycredit.dto.DsaEligibleAccountDto;
import vn.easycredit.entity.DsaEligibleAccountEntity;

@Component
public class DsaEligibleAccountConverter {

	@Autowired
	private ModelMapper mapper;
	
	public DsaEligibleAccountDto convertToDto(DsaEligibleAccountEntity entity) {
		if (StringUtils.isEmpty(entity)) {
			return null;
		}
		DsaEligibleAccountDto result = mapper.map(entity, DsaEligibleAccountDto.class);
		return result;
	}
	
	public DsaEligibleAccountEntity convertToEntity(DsaEligibleAccountDto dto) {
		DsaEligibleAccountEntity result = mapper.map(dto, DsaEligibleAccountEntity.class);
		return result;
	}
}
