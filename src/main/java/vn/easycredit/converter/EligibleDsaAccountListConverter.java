package vn.easycredit.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.easycredit.dto.EligibleDsaAccountListDto;
import vn.easycredit.entity.EligibleDsaAccountListEntity;

@Component
public class EligibleDsaAccountListConverter {

	@Autowired
	private ModelMapper mapper;
	
	public EligibleDsaAccountListDto convertToDto(EligibleDsaAccountListEntity entity) {
		if (StringUtils.isEmpty(entity)) {
			return null;
		}
		EligibleDsaAccountListDto result = mapper.map(entity, EligibleDsaAccountListDto.class);
		return result;
	}
	
	public EligibleDsaAccountListEntity convertToEntity(EligibleDsaAccountListDto dto) {
		EligibleDsaAccountListEntity result = mapper.map(dto, EligibleDsaAccountListEntity.class);
		return result;
	}
}
