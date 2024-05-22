package com.sist.nbgb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlinePaymentApprove;

public interface OnlinePaymentApproveRepository extends JpaRepository<OnlinePaymentApprove, String> 
{
	//강의 글번호, 결제 회원 아이디로 결제 날짜 가져오기
	@Query("select u.approvedAt from OnlinePaymentApprove u where u.itemCode = :itemCode and u.partnerUserId = :partnerUserId and (u.status = 'Y' OR u.status = 'N' OR u.status = 'R') order by u.approvedAt DESC")
	List<LocalDateTime> findApproveAt(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId, Pageable pageable);
	
	List<OnlinePaymentApprove> findAllByPartnerUserIdOrderByApprovedAtDesc(String partnerUserId);
	
	int deleteByPartnerOrderId(String orderId);
	
	Optional<OnlinePaymentApprove> findAllByPartnerOrderId(String orderId);
	
	//강의번호, 유저 아이디로 결제 이력 찾기(재결제 확인용)
	@Query("select u from OnlinePaymentApprove u where u.itemCode = :itemCode and u.partnerUserId = :partnerUserId and (u.status = 'Y' OR u.status = 'N') order by u.approvedAt DESC")
	List<OnlinePaymentApprove> findPay(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId);
	
	//재결제시 기존 결제상태 R로 업데이트
	@Modifying
	@Query("update OnlinePaymentApprove u set u.status = 'R' where u.itemCode = :itemCode and u.partnerUserId = :partnerUserId and u.status in ('Y', 'N')")
	int updatePayStatus(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId);
	
	//재결제시 기존 결제상태 R로 업데이트
	@Modifying
	@Query("update OnlinePaymentApprove u set u.status = 'Y' where u.partnerOrderId = :partnerOrderId")
	int updatePayLogStatus(@Param("partnerOrderId") String partnerOrderId);
		
	Optional<OnlinePaymentApprove> findByPartnerOrderId(String partnerOrderId);
	
	OnlinePaymentApprove findFirstByItemCodeAndPartnerUserId(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId, Sort sort);
	
}
