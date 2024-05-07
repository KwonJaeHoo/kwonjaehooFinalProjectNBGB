package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OnlinePaymentCancel;

public interface OnlinePaymentCancelRepository extends JpaRepository<OnlinePaymentCancel, String>
{
	List<OnlinePaymentCancel> findAllByPartnerUserId(String userId);
}
