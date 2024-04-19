package com.sist.nbgb.dto;


import java.text.DecimalFormat;

import com.sist.nbgb.entity.OnlineClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class OnlineClassList {
	private final String onlineClassTitle;
	private final String instructorNickname;
	private final String onlineClassPrice;
	private final String category;
	
	DecimalFormat df = new DecimalFormat("###,###");
	
	public OnlineClassList(OnlineClass onlineClass) {
		this.onlineClassTitle = onlineClass.getOnlineClassTitle();
		this.instructorNickname = onlineClass.getInstructorId().getInstructorNickname();
		this.onlineClassPrice = df.format(onlineClass.getOnlineClassPrice());
		this.category = onlineClass.getOnlineCategoryId().getOnlineCategoryContent();
	}
	
	
}
