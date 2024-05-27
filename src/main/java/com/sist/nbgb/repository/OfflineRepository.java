package com.sist.nbgb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OfflineClassPaymentListDTO;
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
	

	//페이징 처리(관리자용 오프라인 강의 리스트)
	@Query("SELECT oc FROM OfflineClass oc ORDER BY CASE WHEN oc.offlineClassApprove = 'N' THEN 1 ELSE 2 END, oc.offlineClassRegdate DESC")
    Page<OfflineClass> findAllByApproveStatusAndOrderByRegdateDesc(Pageable pageable);
	
	//검색(관리자용 오프라인 강의 리스트)
	Page<OfflineClass> findByOfflineClassTitleContainingOrOfflineClassContentContaining(String offlineClassTitle, String offlineClassContent, Pageable pageable);
	//강사 마이페이지 
	List<OfflineClass> findByInstructorIdOrderByOfflineClassIdDesc(Instructors instructorId);
	
	//마이페이지 페이징
	Page<OfflineClass> findByInstructorId(Pageable pageable, Instructors instructorId);
	
	//마이페이지 페이징
	@Query("SELECT a FROM OfflineClass a WHERE a.instructorId = (SELECT instructorId FROM Instructors WHERE instructorId = :instructorId) AND a.offlineClassApprove in ('Y', 'B') ORDER BY a.offlineClassId DESC")
	List<OfflineClass> findInsList(@Param("instructorId") String instructorId);
	
	Page<OfflineClass> findByInstructorIdAndOfflineClassApprove(Pageable pageable, Instructors instructorId, Status onlineClassApprove);
	
	Optional<OfflineClass> findByOfflineClassId(Long offlineClassId);
	
	//마이페이지 오프라인 수강목록
	@Query(value = "select distinct p.partnerOrderId as partnerOrderId, p.partnerUserId as partnerUserId, p.itemCode as itemCode, p.bookingDate as bookingDate, "
			+ "p.bookingTime as bookingTime, p.approvedAt as approvedAt, o.offlineClassId as offlineClassId, o.offlineClassTitle as offlineClassTitle, o.instructorId as instructorId "
			+ "from OfflinePaymentApprove p, OfflineClass o "
			+ "where p.itemCode = o.offlineClassId and p.partnerUserId = :partnerUserId and p.status in ('Y') ")
	Page<OfflineClassPaymentListDTO> userOfflineLectureList(@Param("partnerUserId")String partnerUserId, Pageable pageable);
	
	OfflineClass findOfflineClassTitleByOfflineClassId(Long offlineClassId);
	
	OfflineClass findAllByOfflineClassId(Long offlineClassId);
	
	//메인 - 상태가 Y인 강의 목록
	List<OfflineClass> findAllByOfflineClassApproveOrderByOfflineClassViewsDesc(Status offlineClassApprove);


}
