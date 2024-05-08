package com.sist.nbgb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OfflinePayBeforeDto 
{
	private String userId;
	private String userPoint;
	private String offlineClassId;
	private String offlineClassTitle;
	private String usedPoint;
	private String totalAmount;
	private String bookingDate;
	private String resTime;
	
	private String tId; // 결제 고유 번호
	private String pcUrl; // 결제 고유 번호
	
	private String code;
	
	public OfflinePayBeforeDto(String userId,
						String userPoint,
						String offlineClassId,
						String offlineClassTitle,
						String usedPoint,
						String totalAmount,
						String bookingDate,
						String resTime)
	{
		this.userId = userId;
		this.userPoint =userPoint;
		this.offlineClassId = offlineClassId;
		this.offlineClassTitle = offlineClassTitle;
		this.usedPoint = usedPoint;
		this.totalAmount = totalAmount;
		this.bookingDate = bookingDate;
		this.resTime = resTime;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
