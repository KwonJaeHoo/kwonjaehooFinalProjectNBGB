package com.sist.nbgb.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.sist.nbgb.entity.OnlinePaymentCancel;
import com.sist.nbgb.kakao.KakaoPayCancel;

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
	private String cid;
	
	private String tid;
	
	private String partnerOrderId;
	
	private String partnerUserId;
	
	private String itemCode;
	
	private String itemName;
	
	private Long point;
	
	private Long cancelTotalAmount;
	
	private Long cancelTaxFreeAmount;
	
	private LocalDateTime canceledAt;
	
	public OnlinePaymentCancelDto(OnlinePaymentCancel onlinePaymentCancel)
	{
		this.cid = onlinePaymentCancel.getCid();
		this.tid = onlinePaymentCancel.getTid();
		this.partnerOrderId = onlinePaymentCancel.getPartnerOrderId();
		this.partnerUserId = onlinePaymentCancel.getPartnerUserId();
		this.itemCode = onlinePaymentCancel.getItemCode();
		this.itemName = onlinePaymentCancel.getItemName();
		this.point = onlinePaymentCancel.getPoint();
		this.cancelTotalAmount = onlinePaymentCancel.getCancelTotalAmount();
		this.cancelTaxFreeAmount = onlinePaymentCancel.getCancelTaxFreeAmount();
		this.canceledAt = onlinePaymentCancel.getCanceledAt();
	}
	
	public OnlinePaymentCancelDto onlineResult(KakaoPayCancel kakaoPayCancel, KakaoPaymentCancelDto kakaoPaymentCancelDto)
	{
		return OnlinePaymentCancelDto
				.builder()
				.cid(kakaoPayCancel.getCid())
				.tid(kakaoPayCancel.getTid())
				.partnerOrderId(kakaoPayCancel.getPartner_order_id())
				.partnerUserId(kakaoPayCancel.getPartner_user_id())
				.itemCode(kakaoPayCancel.getItem_code())
				.itemName(kakaoPayCancel.getItem_name())
				.point(kakaoPaymentCancelDto.getPoint())
				.cancelTotalAmount((long)kakaoPayCancel.getAmount().getTotal())
				.cancelTaxFreeAmount((long)kakaoPayCancel.getAmount().getTax_free())
				.canceledAt(kakaoPayCancel.getCanceled_at().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
				.build();
	}
	
	
	public OnlinePaymentCancelDto onlineCancel(OnlinePaymentCancel onlinePaymentCancel)
	{
		return OnlinePaymentCancelDto
				.builder()
				.cid(onlinePaymentCancel.getCid())
				.tid(onlinePaymentCancel.getTid())
				.partnerOrderId(onlinePaymentCancel.getPartnerOrderId())
				.partnerUserId(onlinePaymentCancel.getPartnerUserId())
				.itemCode(onlinePaymentCancel.getItemCode())
				.itemName(onlinePaymentCancel.getItemName())
				.point(onlinePaymentCancel.getPoint())
				.cancelTotalAmount(onlinePaymentCancel.getCancelTotalAmount())
				.cancelTaxFreeAmount(onlinePaymentCancel.getCancelTaxFreeAmount())
				.canceledAt(onlinePaymentCancel.getCanceledAt())
				.build();
	}
}