package com.sist.nbgb.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.sist.nbgb.entity.OfflinePaymentCancel;
import com.sist.nbgb.kakao.KakaoPayCancel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflinePaymentCancelDto 
{
	private String cid;
	
	private String tid;
	
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private String bookingDate;
	
	private String bookingTime;
	
	private Long point;
	
	private Long cancelTotalAmount;
	
	private Long cancelTaxFreeAmount;
	
	private LocalDateTime canceledAt;
	
	public OfflinePaymentCancelDto(OfflinePaymentCancel offlinePaymentCancel)
	{
		this.cid = offlinePaymentCancel.getCid();
		this.tid = offlinePaymentCancel.getTid();
		this.partnerOrderId = offlinePaymentCancel.getPartnerOrderId();
		this.partnerUserId = offlinePaymentCancel.getPartnerUserId();
		this.itemCode = offlinePaymentCancel.getItemCode();
		this.itemName = offlinePaymentCancel.getItemName();
		this.bookingDate = offlinePaymentCancel.getBookingDate();
		this.bookingTime = offlinePaymentCancel.getBookingTime();
		this.point = offlinePaymentCancel.getPoint();
		this.cancelTotalAmount = offlinePaymentCancel.getCancelTotalAmount();
		this.cancelTaxFreeAmount = offlinePaymentCancel.getCancelTaxFreeAmount();
		this.canceledAt = offlinePaymentCancel.getCanceledAt();
	}
	
	public OfflinePaymentCancelDto offlineResult(KakaoPayCancel kakaoPayCancel, KakaoPaymentCancelDto kakaoPaymentCancelDto)
	{
		return OfflinePaymentCancelDto
				.builder()
				.cid(kakaoPayCancel.getCid())
				.tid(kakaoPayCancel.getTid())
				.partnerOrderId(kakaoPayCancel.getPartner_order_id())
				.partnerUserId(kakaoPayCancel.getPartner_user_id())
				.itemCode(kakaoPayCancel.getItem_code())
				.itemName(kakaoPayCancel.getItem_name())
				.bookingDate(kakaoPaymentCancelDto.getBookingDate())
				.bookingTime(kakaoPaymentCancelDto.getBookingTime())
				.point(kakaoPaymentCancelDto.getPoint())
				.cancelTotalAmount((long)kakaoPayCancel.getAmount().getTotal())
				.cancelTaxFreeAmount((long)kakaoPayCancel.getAmount().getTax_free())
				.canceledAt(kakaoPayCancel.getCanceled_at().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
				.build();
	}
	
	
	public OfflinePaymentCancelDto offlineCancel(OfflinePaymentCancel offlinePaymentCancel)
	{
		return OfflinePaymentCancelDto
				.builder()
				.cid(offlinePaymentCancel.getCid())
				.tid(offlinePaymentCancel.getTid())
				.partnerOrderId(offlinePaymentCancel.getPartnerOrderId())
				.partnerUserId(offlinePaymentCancel.getPartnerUserId())
				.itemCode(offlinePaymentCancel.getItemCode())
				.itemName(offlinePaymentCancel.getItemName())
				.bookingDate(offlinePaymentCancel.getBookingDate())
				.bookingTime(offlinePaymentCancel.getBookingTime())
				.point(offlinePaymentCancel.getPoint())
				.cancelTotalAmount(offlinePaymentCancel.getCancelTotalAmount())
				.cancelTaxFreeAmount(offlinePaymentCancel.getCancelTaxFreeAmount())
				.canceledAt(offlinePaymentCancel.getCanceledAt())
				.build();
	}
}