package com.sist.nbgb.dto;

import lombok.Getter;

@Getter
public class OfflinePopDto
{
	private String userId;
	private String userPoint;
	private String offlineClassId;
	private String offlineClassTitle;
	private String offlineClassPlace;
	private String offlineClassPrice;
	private String resDay;
	private String bookingDate;
	private String resTime;
	
	public OfflinePopDto(String userId,
						String userPoint,
						String offlineClassId,
						String offlineClassTitle,
						String offlineClassPlace,
						String offlineClassPrice,
						String resDay,
						String bookingDate,
						String resTime)
	{
		this.userId = userId;
		this.userPoint =userPoint;
		this.offlineClassId = offlineClassId;
		this.offlineClassTitle = offlineClassTitle;
		this.offlineClassPlace = offlineClassPlace;
		this.offlineClassPrice = offlineClassPrice;
		this.resDay = resDay;
		this.bookingDate = bookingDate;
		this.resTime = resTime;
	}
}
