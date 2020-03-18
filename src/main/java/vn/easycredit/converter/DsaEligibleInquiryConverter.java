package vn.easycredit.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.easycredit.dto.DsaEligibleInquiryDto;
import vn.easycredit.entity.DsaEligibleInquiryEntity;

@Component
public class DsaEligibleInquiryConverter {

	@Autowired
	private ModelMapper mapper;
	
	public DsaEligibleInquiryDto convertToDto(DsaEligibleInquiryEntity entity) {
		if (StringUtils.isEmpty(entity)) {
			return null;
		}
		DsaEligibleInquiryDto result = mapper.map(entity, DsaEligibleInquiryDto.class);
		return result;
	}
	
	public DsaEligibleInquiryEntity convertToEntity(DsaEligibleInquiryDto dto) {
		DsaEligibleInquiryEntity result = mapper.map(dto, DsaEligibleInquiryEntity.class);
		return result;
	}
}
