package com.sist.nbgb.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OnlinePaymentApprove;

public interface OnlinePaymentApproveRepository extends JpaRepository<OnlinePaymentApprove, String> {
	//강의 글번호, 결제 회원 아이디로 결제 날짜 가져오기
	//LocalDateTime findByItemCodeAndPartnerUserId(String itemCode, String partnerUserId);
}
