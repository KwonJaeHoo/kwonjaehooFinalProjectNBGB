package com.sist.nbgb.repository;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;



public interface OnlineClassRepository extends JpaRepository<OnlineClass, Long>{
	
	//전체 리스트 조회(최신순)
	List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassIdDesc(Status onlineClassApprove);
	
	//카테고리별 리스트 조회
	List<OnlineClass> findByOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(long onlineCategoryId, Status onlineClassApprove);
	
	//검색 기능
	List<OnlineClass> findByOnlineClassTitleContainingAndOnlineClassApprove(String searchKeyword, Status onlineClassApprove);
	
	//카테고리 내 검색
	List<OnlineClass> findByOnlineClassTitleContainingAndOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(String searchKeyword, long onlineCategoryId, Status onlineClassApprove);

	//정렬기준 - 조회 순
 	List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassViews(Status onlineClassApprove);
	
	//정렬기준 - 가격 낮은 순
	List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassPriceAsc(Status onlineClassApprove);
	
	//정렬기준 - 가격 높은 순
	List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassPriceDesc(Status onlineClassApprove);
	
	//조회수 증가
	@Modifying
	@Query("update OnlineClass o set o.onlineClassViews = o.onlineClassViews + 1 where o.onlineClassId = :onlineClassId")
	int updateViews(@Param("onlineClassId") Long onlineClassId);
	
	//찜 목록 추가 시 classId, classIden 가져오기
	OnlineClass findFirstByonlineClassId(Long onlineClassId);
	
	//마이페이지 수강목록
	@Query(value = "select p.partnerUserId as partnerUserId, p.itemCode as itemCode, p.approvedAt as approvedAt, "
			+ "o.onlineClassId as onlineClassId, o.onlineClassTitle as onlineClassTitle, o.instructorId as instructorId, o.onlineClassPeriod as onlineClassPeriod "
			+ "from OnlinePaymentApprove p, OnlineClass o "
			+ "where p.itemCode = o.onlineClassId and p.partnerUserId = :partnerUserId and p.status in ('N', 'Y') ")
	List<OnlinePaymentClassListDTO> userLectureList(@Param("partnerUserId")String partnerUserId , Sort sort);
	
	//수강정보 및 결제정보
	@Query(value = "select p.partnerUserId as partnerUserId, p.itemCode as itemCode, p.approvedAt as approvedAt, "
			+ "o.onlineClassId as onlineClassId, o.onlineClassTitle as onlineClassTitle, o.instructorId as instructorId, o.onlineClassPeriod as onlineClassPeriod "
			+ "from OnlinePaymentApprove p, OnlineClass o "
			+ "where p.itemCode = o.onlineClassId and p.partnerUserId = :partnerUserId and o.onlineClassId = :onlineClassId")
	OnlinePaymentClassListDTO userLectureInfo(@Param("partnerUserId")String partnerUserId , @Param("onlineClassId")Long onlineClassId);
}
