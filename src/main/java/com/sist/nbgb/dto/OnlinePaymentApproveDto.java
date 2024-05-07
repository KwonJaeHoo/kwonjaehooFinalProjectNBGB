package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlinePaymentApprove;
import com.sist.nbgb.enums.Status;

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
public class OnlinePaymentApproveDto 
{
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private Long point;
	
	private Long totalAmount;
	
	private LocalDateTime approvedAt;
	
	private Status status;
	
	public OnlinePaymentApproveDto(OnlinePaymentApprove onlinePaymentApprove)
	{
		this.partnerOrderId = onlinePaymentApprove.getPartnerOrderId();
		this.partnerUserId = onlinePaymentApprove.getPartnerUserId();
		this.itemCode = onlinePaymentApprove.getItemCode();
		this.itemName = onlinePaymentApprove.getItemName();
		this.point = onlinePaymentApprove.getPoint();
		this.totalAmount = onlinePaymentApprove.getTotalAmount();
		this.approvedAt = onlinePaymentApprove.getApprovedAt();
		this.status = onlinePaymentApprove.getStatus();
	}
}