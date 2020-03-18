package vn.easycredit.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.converter.DsaEligibleInquiryConverter;
import vn.easycredit.dto.DsaEligibleInquiryDto;
import vn.easycredit.repository.DsaEligibleInquiryRepository;

@Service
@Slf4j
public class DsaEligibleInquiryService {
	
	@Autowired
	private DsaEligibleInquiryConverter inquiryConverter;
	
	@Autowired
	private DsaEligibleInquiryRepository inquiryRepository;
	
	public void save (DsaEligibleInquiryDto dto) {
		try {
			inquiryRepository.save(inquiryConverter.convertToEntity(dto));
		} catch (PersistenceException e) {
			log.info("erro save...");
			log.info(e.toString());
			throw new PersistenceException();
		}
	}
}
