package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OfflinePaymentApprove;

public interface OfflinePaymentApproveRepository extends JpaRepository<OfflinePaymentApprove, String>
{
	List<OfflinePaymentApprove> findAllByPartnerUserId(String userId);
}
