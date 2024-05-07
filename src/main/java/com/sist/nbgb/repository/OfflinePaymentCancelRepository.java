package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OfflinePaymentCancel;

public interface OfflinePaymentCancelRepository extends JpaRepository<OfflinePaymentCancel, String>
{
	List<OfflinePaymentCancel> findAllByPartnerUserId(String userId);
}
