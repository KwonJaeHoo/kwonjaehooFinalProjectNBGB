package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;

@Transactional
@Repository
public interface OfflineRepository extends JpaRepository<OfflineClass, Long>
{
	@Query("SELECT m FROM OfflineClass m WHERE m.offlineClassApprove = :offlineClassApprove ORDER BY m.offlineClassId DESC")
	List<OfflineClass> findByUpload(@Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT a, b.instructorNickname " +
		       "FROM OfflineClass a, Instructors b " +
		       "WHERE a.instructorId = b.instructorId " +
		       "AND (a.offlineClassTitle LIKE %:search1% " +
		       "OR b.instructorNickname LIKE %:search1%) " +
		       "AND a.offlineClassApprove = :offlineClassApprove " +
		       "ORDER BY a.offlineClassId DESC")
	List<OfflineClass> findBySearch(@Param("search1") String search1, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT m FROM OfflineClass m WHERE m.offlineClassCategory LIKE %:search% AND m.offlineClassApprove = :offlineClassApprove ORDER BY m.offlineClassId DESC")
	List<OfflineClass> findByCategory(@Param("search") String search, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT m FROM OfflineClass m WHERE m.offlineClassPlace LIKE %:search% AND m.offlineClassApprove = :offlineClassApprove ORDER BY m.offlineClassId DESC")
	List<OfflineClass> findByPalce(@Param("search") String search, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT m FROM OfflineClass m WHERE (m.offlineClassPlace LIKE %:search1% OR m.offlineClassPlace LIKE %:search2%) AND m.offlineClassApprove = :offlineClassApprove ORDER BY m.offlineClassId DESC")
	List<OfflineClass> findByTwoPlace(@Param("search1") String search1, @Param("search2") String search21, @Param("offlineClassApprove") Status offlineClassApprove);
	
	//중복검색
	@Query("SELECT a, b.instructorNickname " +
		       "FROM OfflineClass a, Instructors b " +
		       "WHERE a.instructorId = b.instructorId " +
		       "AND (a.offlineClassTitle LIKE %:search1% " +
		       "OR b.instructorNickname LIKE %:search1%) " +
		       "AND a.offlineClassCategory LIKE %:search2% " +
		       "AND a.offlineClassApprove = :offlineClassApprove " +
		       "ORDER BY a.offlineClassId DESC")
	List<OfflineClass> findCateKeyword(@Param("search1") String search1, @Param("search2") String search2, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT a, b.instructorNickname " +
		       "FROM OfflineClass a, Instructors b " +
		       "WHERE a.instructorId = b.instructorId " +
		       "AND (a.offlineClassTitle LIKE %:search1% " +
		       "OR b.instructorNickname LIKE %:search1%) " +
		       "AND a.offlineClassPlace LIKE %:search2% " +
		       "AND a.offlineClassApprove = :offlineClassApprove " +
		       "ORDER BY a.offlineClassId DESC")
	List<OfflineClass> findPlaceKeyword(@Param("search1") String search1, @Param("search2") String search2, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT a, b.instructorNickname " +
		       "FROM OfflineClass a, Instructors b " +
		       "WHERE a.instructorId = b.instructorId " +
		       "AND (a.offlineClassTitle LIKE %:search1% " +
		       "OR b.instructorNickname LIKE %:search1%) " +
		       "AND (a.offlineClassPlace LIKE %:search2% OR a.offlineClassPlace LIKE %:search3%) " +
		       "AND a.offlineClassApprove = :offlineClassApprove " +
		       "ORDER BY a.offlineClassId DESC")
	List<OfflineClass> findTwoPlaceKeyword(@Param("search1") String search1, @Param("search2") String search2, @Param("search3") String search3, @Param("offlineClassApprove") Status offlineClassApprove);
	
	@Query("SELECT a, b.instructorNickname " +
		       "FROM OfflineClass a, Instructors b " +
		       "WHERE a.instructorId = b.instructorId " +
		       "AND a.offlineClassId = :offlineClassId ")
	OfflineClass findByView(@Param("offlineClassId") Long offlineClassId);
	
	//조회수 증가
	@Modifying
	@Query("UPDATE OfflineClass o SET o.offlineClassViews = o.offlineClassViews + 1 where o.offlineClassId = :offlineClassId")
	int updateViews(@Param("offlineClassId") Long offlineClassId);
	
	//강사 마이페이지 
	List<OfflineClass> findByInstructorIdOrderByOfflineClassIdDesc(Instructors instructorId);
	
	//마이페이지 페이징
	Page<OfflineClass> findByInstructorId(Pageable pageable, Instructors instructorId);
	
	//마이페이지 페이징
	Page<OfflineClass> findByInstructorIdAndOfflineClassApprove(Pageable pageable, Instructors instructorId, Status onlineClassApprove);
}
