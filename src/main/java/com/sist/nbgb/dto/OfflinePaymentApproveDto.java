package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OfflinePaymentApprove;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflinePaymentApproveDto 
{
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private String bookingDate;
	
	private String bookingTime;

	private Long point;
	
	private Long totalAmount;

	private LocalDateTime approvedAt;
	
	public OfflinePaymentApproveDto(OfflinePaymentApprove offlinePaymentApprove)
	{
		this.partnerOrderId = offlinePaymentApprove.getPartnerOrderId();
		this.partnerUserId = offlinePaymentApprove.getPartnerUserId();
		this.itemCode = offlinePaymentApprove.getItemCode();
		this.itemName = offlinePaymentApprove.getItemName();
		this.bookingDate = offlinePaymentApprove.getBookingDate();
		this.bookingTime = offlinePaymentApprove.getBookingTime();
		this.point = offlinePaymentApprove.getPoint();
		this.totalAmount = offlinePaymentApprove.getTotalAmount();
		this.approvedAt = offlinePaymentApprove.getApprovedAt();
	}
}