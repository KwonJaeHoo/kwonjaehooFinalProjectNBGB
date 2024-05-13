package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OnlinePayDto {
	private String tid;	
	private String pcUrl;	
	private String orderId;
	private String pgToken;
	private String partnerUserId;
	private String usedPoint;
	
	public OnlinePayDto(String tid, String pcUrl, String orderId, String pgToken, String partnerUserId, String usedPoint)
	{
		this.tid = tid;
		this.pcUrl = pcUrl;
		this.orderId = orderId;
		this.pgToken = pgToken;
		this.partnerUserId = partnerUserId;
		this.usedPoint = usedPoint;
	}
}
