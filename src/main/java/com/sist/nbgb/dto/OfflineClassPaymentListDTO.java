package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Instructors;

public interface OfflineClassPaymentListDTO {
	String getPartnerOrderId();
	String getPartnerUserId();
	String getItemCode();
	String getBookingDate();
	String getBookingTime();
	LocalDateTime getApprovedAt();
	
	Long getOfflineClassId();
	String getOfflineClassTitle();
	Instructors getInstructorId();
}
