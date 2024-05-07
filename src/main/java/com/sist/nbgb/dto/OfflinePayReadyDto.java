package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OfflinePayReadyDto 
{	
	private String tid;	
	
	private String pcUrl;	
	
	private String orderId;
	
	public OfflinePayReadyDto(String tid, String pcUrl, String orderId)
	{
		this.tid = tid;
		this.pcUrl = pcUrl;
		this.orderId = orderId;
	}
}
