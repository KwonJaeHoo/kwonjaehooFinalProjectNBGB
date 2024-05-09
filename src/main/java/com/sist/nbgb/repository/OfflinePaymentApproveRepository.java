package com.sist.nbgb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OfflinePaymentApprove;

public interface OfflinePaymentApproveRepository extends JpaRepository<OfflinePaymentApprove, String>
{
	List<OfflinePaymentApprove> findAllByPartnerUserId(String userId);
	
	int deleteByPartnerOrderId(String orderId);
	
	Optional<OfflinePaymentApprove> findAllByPartnerOrderId(String orderId);
	
	@Modifying
	@Query("UPDATE OfflinePaymentApprove "
			+ "SET cid = :cid "
			+ ", tid = :tid "
			+ ", taxFreeAmount = 0 "
			+ ", approvedAt = SYSDATE "
			+ "WHERE partnerOrderId = :orderId")
	int updatePay(@Param("orderId") String orderId, @Param("tid") String tId, @Param("cid") String cid );
	
	@Query("SELECT COUNT(partner_order_id) "
			+ "FROM OfflinePaymentApprove "
			+ "WHERE itemCode = :classId "
			+ "AND bookingDate = :date "
			+ "AND bookingTime = :time ")
	Long countPeople(@Param("classId") String classId, @Param("date") String date, @Param("time") String time);
}
