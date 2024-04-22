package com.sist.nbgb.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlinePaymentApprove;

public interface OnlinePaymentApproveRepository extends JpaRepository<OnlinePaymentApprove, String> 
{
	//강의 글번호, 결제 회원 아이디로 결제 날짜 가져오기
	@Query("select u.approvedAt from OnlinePaymentApprove u where u.itemCode = :itemCode and u.partnerUserId = :partnerUserId")
	LocalDateTime findApproveAt(@Param("itemCode") String itemCode, @Param("partnerUserId") String partnerUserId);
}
