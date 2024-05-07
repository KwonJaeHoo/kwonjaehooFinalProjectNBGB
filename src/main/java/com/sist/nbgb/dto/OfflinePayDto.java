package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OfflinePayDto 
{	
	private String tid;	
	
	private String pcUrl;	
	
	private String orderId;
	
	private String pgToken;
	
	public OfflinePayDto(String tid, String pcUrl, String orderId, String pgToken)
	{
		this.tid = tid;
		this.pcUrl = pcUrl;
		this.orderId = orderId;
		this.pgToken = pgToken;
	}
}