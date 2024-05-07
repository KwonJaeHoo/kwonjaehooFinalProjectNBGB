package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OfflinePaymentCancel;

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
public class OfflinePaymentCancelDto 
{
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private String bookingDate;
	
	private String bookingTime;
	
	private Long point;
	
	private Long cancelTotalAmount;
	
	private LocalDateTime canceledAt;
	
	public OfflinePaymentCancelDto(OfflinePaymentCancel offlinePaymentCancel)
	{
		this.partnerOrderId = offlinePaymentCancel.getPartnerOrderId();
		this.partnerUserId = offlinePaymentCancel.getPartnerUserId();
		this.itemCode = offlinePaymentCancel.getItemCode();
		this.itemName = offlinePaymentCancel.getItemName();
		this.bookingDate = offlinePaymentCancel.getBookingDate();
		this.bookingTime = offlinePaymentCancel.getBookingTime();
		this.point = offlinePaymentCancel.getPoint();
		this.cancelTotalAmount = offlinePaymentCancel.getCancelTotalAmount();
		this.canceledAt = offlinePaymentCancel.getCanceledAt();
	}
}