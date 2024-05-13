package com.sist.nbgb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OnlinePayBeforeDto {
	private String userId;
	private String userPoint;
	private String onlineClassId;
	private String onlineClassTitle;
	private String onlineClassPrice;
	private String onlineClassPeriod;
	private String totalAmount;
	private String usedPoint;
	
	public OnlinePayBeforeDto(String userId,
						String userPoint,
						String onlineClassId,
						String onlineClassTitle,
						String onlineClassPrice,
						String onlineClassPeriod,
						String totalAmount,
						String usedPoint)
	{
		this.userId = userId;
		this.userPoint =userPoint;
		this.onlineClassId = onlineClassId;
		this.onlineClassTitle = onlineClassTitle;
		this.onlineClassPrice = onlineClassPrice;
		this.onlineClassPeriod = onlineClassPeriod;
		this.totalAmount = totalAmount;
		this.usedPoint = usedPoint;
	}
}
