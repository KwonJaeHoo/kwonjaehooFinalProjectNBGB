package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class OnlineInsDto {
	private final Long onlineClassId;
	private final String onlineClassTitle;
	private final String onlineClassContent;
	private final LocalDateTime onlineClassRegdate;
	private final String instructorId;
	private final String instructorNickname;
	private final Long onlineClassPrice;
	private final Long onlineClassPeriod;
	private final Long onlineClassViews;
	private final String category;
	private final Status onlineClassApprove;
	private final String rejection;
	private final LocalDateTime rejectionRegDate;
	
	public OnlineInsDto(OnlineClass onlineClass) {
		this.onlineClassId = onlineClass.getOnlineClassId();
		this.onlineClassTitle = onlineClass.getOnlineClassTitle();
		this.onlineClassContent = onlineClass.getOnlineClassContent();
		this.onlineClassRegdate = onlineClass.getOnlineClassRegdate();
		this.instructorId = onlineClass.getInstructorId().getInstructorId();
		this.instructorNickname= onlineClass.getInstructorId().getInstructorNickname();
		this.onlineClassPrice = onlineClass.getOnlineClassPrice();
		this.onlineClassPeriod = onlineClass.getOnlineClassPeriod();
		this.onlineClassViews = onlineClass.getOnlineClassViews();
		this.category = onlineClass.getOnlineCategoryId().getOnlineCategoryContent();
		this.onlineClassApprove = onlineClass.getOnlineClassApprove();
		this.rejection = onlineClass.getRejection();
		this.rejectionRegDate = onlineClass.getRejectionRegdate();
	}
}

