package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OnlinePayReadyDto {
	private String tid;	
	private String pcUrl;	
	private String orderId;
	private String usedPoint; //
	
	public OnlinePayReadyDto(String tid, String pcUrl, String orderId, String usedPoint)
	{
		this.tid = tid;
		this.pcUrl = pcUrl;
		this.orderId = orderId;
		this.usedPoint = usedPoint;
	}
}

