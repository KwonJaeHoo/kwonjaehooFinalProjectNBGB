package com.sist.nbgb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlinePaymentApprove;
import com.sist.nbgb.enums.Status;

public interface OnlinePaymentApproveRepository extends JpaRepository<OnlinePaymentApprove, String> 
{
	//강의 글번호, 결제 회원 아이디로 결제 날짜 가져오기
	@Query("select u.approvedAt from OnlinePaymentApprove u where u.itemCode = :itemCode and u.partnerUserId = :partnerUserId and u.status = 'Y' order by u.approvedAt DESC")
	LocalDateTime findApproveAt(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId);
	
	List<OnlinePaymentApprove> findAllByPartnerUserId(String partnerUserId);
	
}
