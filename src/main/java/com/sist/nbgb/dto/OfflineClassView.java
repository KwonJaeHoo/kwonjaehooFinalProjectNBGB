package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OfflineClass;

import lombok.Getter;

@Getter
public class OfflineClassView {
	private final String offlineClassTitle;
	private final String offlineClassContent;
	private final LocalDateTime offlineClassRegdate;
	private final String instructorId;
	private final String instructorNickname;
	private final Long offlineClassPrice;
	private final Long offlineClassViews;
	private final String category;
	
	public OfflineClassView(OfflineClass offlineClass) {
		this.offlineClassTitle = offlineClass.getOfflineClassTitle();
		this.offlineClassContent = offlineClass.getOfflineClassContent();
		this.offlineClassRegdate = offlineClass.getOfflineClassRegdate();
		this.instructorId = offlineClass.getInstructorId().getInstructorId();
		this.instructorNickname = offlineClass.getInstructorId().getInstructorNickname();
		this.offlineClassPrice = offlineClass.getOfflineClassPrice();
		this.offlineClassViews = offlineClass.getOfflineClassViews();
		this.category = offlineClass.getOfflineClassCategory();
	}
}
