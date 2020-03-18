package vn.easycredit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.easycredit.entity.DsaEligibleAccountEntity;

public interface DsaEligibleAccountRepository extends JpaRepository<DsaEligibleAccountEntity, String>{
	
	@Query("SELECT dsa FROM DsaEligibleAccountEntity dsa WHERE dsa.tokenCreatePassword = :tokenCreatePassword ORDER BY dsa.createdDate DESC")
	List<DsaEligibleAccountEntity> findByTokenCreatePassword(@Param("tokenCreatePassword") final String tokenCreatePassword);
	
	@Query("SELECT dsa FROM DsaEligibleAccountEntity dsa WHERE dsa.tokenForgotPassword = :tokenForgotPassword ORDER BY dsa.createdDate DESC")
	List<DsaEligibleAccountEntity> findByTokenForgotPassword(@Param("tokenForgotPassword") final String tokenForgotPassword);
	
	@Query("SELECT dsa FROM DsaEligibleAccountEntity dsa WHERE dsa.userName = :userName AND dsa.tokenCreatePassword = :tokenCreatePassword ORDER BY dsa.createdDate DESC")
	List<DsaEligibleAccountEntity> findByUserNameByTokenCreatePassword(@Param("userName") final String userName, @Param("tokenCreatePassword") final String tokenCreatePassword);
	
	@Query("SELECT dsa FROM DsaEligibleAccountEntity dsa WHERE dsa.userName = :userName AND dsa.tokenForgotPassword = :tokenForgotPassword ORDER BY dsa.createdDate DESC")
	List<DsaEligibleAccountEntity> findByUserNameByTokenForgotPassword(@Param("userName") final String userName, @Param("tokenForgotPassword") final String tokenForgotPassword);
}
