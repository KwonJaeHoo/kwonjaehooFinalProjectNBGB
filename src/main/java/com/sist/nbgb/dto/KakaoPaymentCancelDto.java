package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.OnlinePaymentApprove;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPaymentCancelDto 
{
	private String tid;
	
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private Long totalAmount;
	
	private Long taxFreeAmount;
	
	private Long point;
	
	private Status status;
	
	private String bookingDate;

	private String bookingTime;
	
	
	public static KakaoPaymentCancelDto onlinePayment(OnlinePaymentApprove onlinePaymentApprove)
	{
		return KakaoPaymentCancelDto
				.builder()
				.tid(onlinePaymentApprove.getTid())
				.partnerOrderId(onlinePaymentApprove.getPartnerOrderId())
				.partnerUserId(onlinePaymentApprove.getPartnerUserId())
				.itemCode(onlinePaymentApprove.getItemCode())
				.itemName(onlinePaymentApprove.getItemName())
				.totalAmount(onlinePaymentApprove.getTotalAmount())
				.taxFreeAmount(onlinePaymentApprove.getTaxFreeAmount())
				.point(onlinePaymentApprove.getPoint())
				.status(onlinePaymentApprove.getStatus())
				.build();
	}
	
	public static KakaoPaymentCancelDto offlinePayment(OfflinePaymentApprove offlinePaymentApprove)
	{
		return KakaoPaymentCancelDto
				.builder()
				.tid(offlinePaymentApprove.getTid())
				.partnerOrderId(offlinePaymentApprove.getPartnerOrderId())
				.partnerUserId(offlinePaymentApprove.getPartnerUserId())
				.itemCode(offlinePaymentApprove.getItemCode())
				.itemName(offlinePaymentApprove.getItemName())
				.totalAmount(offlinePaymentApprove.getTotalAmount())
				.taxFreeAmount(offlinePaymentApprove.getTaxFreeAmount())
				.point(offlinePaymentApprove.getPoint())
				.status(offlinePaymentApprove.getStatus())
				.bookingDate(offlinePaymentApprove.getBookingDate())
				.bookingTime(offlinePaymentApprove.getBookingTime())
				.build();
	}
}
