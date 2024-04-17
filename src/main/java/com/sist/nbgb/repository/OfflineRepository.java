package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;

@Repository
public interface OfflineRepository extends JpaRepository<OfflineClass, Long>
{
	@Query("SELECT m FROM OfflineClass m WHERE m.offlineClassApprove = :offlineClassApprove")
	List<OfflineClass> findByUpload(@Param("offlineClassApprove") Status offlineClassApprove);
	
	List<OfflineClass> findByOfflineClassCategoryContaining(String search);
	
	List<OfflineClass> findByOfflineClassPlaceContaining(String search);
	
	List<OfflineClass> findByOfflineClassPlaceContainingOrOfflineClassPlaceContaining(String search1, String search2);
}
