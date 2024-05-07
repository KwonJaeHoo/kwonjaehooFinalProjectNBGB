package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlinePaymentCancel;

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
public class OnlinePaymentCancelDto 
{
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private Long point;
	
	private Long cancelTotalAmount;
	
	private LocalDateTime canceledAt;
	
	public OnlinePaymentCancelDto(OnlinePaymentCancel onlinePaymentCancel)
	{
		this.partnerOrderId = onlinePaymentCancel.getPartnerOrderId();
		this.partnerUserId = onlinePaymentCancel.getPartnerUserId();
		this.itemCode = onlinePaymentCancel.getItemCode();
		this.itemName = onlinePaymentCancel.getItemName();
		this.point = onlinePaymentCancel.getPoint();
		this.cancelTotalAmount = onlinePaymentCancel.getCancelTotalAmount();
		this.canceledAt = onlinePaymentCancel.getCanceledAt();
	}
}
