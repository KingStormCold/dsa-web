package vn.easycredit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.easycredit.entity.DsaEligibleLoginHistoryEntity;

public interface DsaEligibleLoginHistoryRepository extends JpaRepository<DsaEligibleLoginHistoryEntity, String>{

	@Query("SELECT delh FROM DsaEligibleLoginHistoryEntity delh WHERE delh.loginId =:loginId AND delh.userName =:userName")
	DsaEligibleLoginHistoryEntity findByLoginIdAndUserName(@Param("loginId") String loginId, @Param("userName") String userName);
	
	@Query(value = "SELECT delh.* FROM dsa_eligible_login_history delh WHERE delh.user_name = :userName ORDER BY delh.created_login DESC LIMIT 1", nativeQuery = true)
	DsaEligibleLoginHistoryEntity findByUserName(@Param("userName") String userName);
}
