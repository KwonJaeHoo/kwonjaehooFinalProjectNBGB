package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OnlinePopDTO {
	private String userId;
	private String userPoint;
	private String onlineClassId;
	private String onlineClassTitle;
	private String onlineClassPrice;
	private String onlineClassPeriod;
	private String totalAmount;
	
	public OnlinePopDTO(String userId,
						String userPoint,
						String onlineClassId,
						String onlineClassTitle,
						String onlineClassPrice,
						String onlineClassPeriod,
						String totalAmount)
	{
		this.userId = userId;
		this.userPoint =userPoint;
		this.onlineClassId = onlineClassId;
		this.onlineClassTitle = onlineClassTitle;
		this.onlineClassPrice = onlineClassPrice;
		this.onlineClassPeriod = onlineClassPeriod;
		this.totalAmount = totalAmount;
	}
}
