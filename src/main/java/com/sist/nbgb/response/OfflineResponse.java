package com.sist.nbgb.response;

import java.time.LocalTime;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class OfflineResponse
{
	private Long offlineClassId;
	
	private String offlineClassTitle;
	
	private String offlineClassContent;
	
	private LocalTime offlineClassRegdate;
	
	private String offlineClassPlace;
	
	private String offlineClassCategory;
	
	private Instructors instructorId;
	
	private String instructorNickname;
	
	private Long offlineClassPrice;
	
	private Status offlineClassApprove;
	
	private Long offlineClassLimitPeople;
	
	private Long offlineClassViews;
	
	public OfflineResponse(OfflineClass classes)
	{
		this.offlineClassId = classes.getOfflineClassId();
		this.offlineClassTitle = classes.getOfflineClassTitle();
		this.offlineClassContent = classes.getOfflineClassContent();
		this.offlineClassRegdate = classes.getOfflineClassRegdate();
		this.offlineClassPlace = classes.getOfflineClassPlace();
		this.offlineClassCategory = classes.getOfflineClassCategory();
		this.instructorId = classes.getInstructorId();
		this.instructorNickname = classes.getInstructorId().getInstructorNickname();
		this.offlineClassPrice = classes.getOfflineClassPrice();
		this.offlineClassApprove = classes.getOfflineClassApprove();
		this.offlineClassLimitPeople = classes.getOfflineClassLimitPeople();
		this.offlineClassViews = classes.getOfflineClassViews();
	}
}
