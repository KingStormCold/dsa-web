package vn.easycredit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.easycredit.entity.EligibleDsaAccountListEntity;

public interface EligibleDsaAccountListReponsitory extends JpaRepository<EligibleDsaAccountListEntity, String>{

	@Query("SELECT dsa FROM EligibleDsaAccountListEntity dsa WHERE dsa.status = :status")
	List<EligibleDsaAccountListEntity> findByStatus(@Param("status") String status);
}
