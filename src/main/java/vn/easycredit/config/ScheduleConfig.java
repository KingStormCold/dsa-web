package vn.easycredit.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import vn.easycredit.dto.DsaEligibleLoginHistoryDto;
import vn.easycredit.service.DsaEligibleService;

@Component
@Slf4j
public class ScheduleConfig {
	
	@Autowired
	private DsaEligibleService dsaEligibleService;
	
	public Map<String, DsaEligibleLoginHistoryDto> mapLoginHistory;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 24, initialDelay = 1000 * 60)
	public void runCreateOrBLockAccount() {
		log.info("create account");
		dsaEligibleService.createAccountDSA();
		log.info("block account");
		dsaEligibleService.checkStatusAndBlockAccount();
	}
	
	@Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 1000 * 60)
	public void runCheckLoginHistory() {
		if (this.mapLoginHistory == null) {
			this.mapLoginHistory = new HashMap<>();
		}
		List<String> removeValues = new ArrayList<>();
		for (Map.Entry<String, DsaEligibleLoginHistoryDto> loginHistory : this.mapLoginHistory.entrySet()) {
			boolean checkLoginHistory = dsaEligibleService.checkLoginHistory(loginHistory.getValue());
			if (checkLoginHistory) {
				removeValues.add(loginHistory.getKey());
			}
		}
		for (String value : removeValues) {
			this.mapLoginHistory.remove(value);
		}
	}
}
