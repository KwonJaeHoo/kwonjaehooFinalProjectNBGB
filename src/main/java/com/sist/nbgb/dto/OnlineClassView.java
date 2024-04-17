package com.sist.nbgb.dto;

import java.time.LocalTime;

import com.sist.nbgb.entity.OnlineClass;

import lombok.Getter;

@Getter
public class OnlineClassView {
	private final String onlineClassTitle;
	private final String onlineClassContent;
	private final LocalTime onlineClassRegdate;
	private final String instructorId;
	private final String instructorNickname;
	private final Long onlineClassPrice;
	private final Long onlineClassPeriod;
	private final Long onlineClassViews;
	private final String category;
	
	public OnlineClassView(OnlineClass onlineClass) {
		this.onlineClassTitle = onlineClass.getOnlineClassTitle();
		this.onlineClassContent = onlineClass.getOnlineClassContent();
		this.onlineClassRegdate = onlineClass.getOnlineClassRegdate();
		this.instructorId = onlineClass.getInstructorId().getInstructorId();
		this.instructorNickname= onlineClass.getInstructorId().getInstructorNickname();
		this.onlineClassPrice = onlineClass.getOnlineClassPrice();
		this.onlineClassPeriod = onlineClass.getOnlineClassPeriod();
		this.onlineClassViews = onlineClass.getOnlineClassViews();
		this.category = onlineClass.getOnlineCategoryId().getOnlineCategoryContent();
	}
}
