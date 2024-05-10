package com.sist.nbgb.dto;


import java.text.DecimalFormat;

import com.sist.nbgb.entity.OnlineClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class OnlineClassListDTO {
	private Long onlineClassId;
	private String onlineClassTitle;
	private String instructorNickname;
	private String onlineClassPrice;
	private String category;
	
	DecimalFormat df = new DecimalFormat("###,###");
	
	public OnlineClassListDTO(OnlineClass onlineClass) {
		this.onlineClassId = onlineClass.getOnlineClassId();
		this.onlineClassTitle = onlineClass.getOnlineClassTitle();
		this.instructorNickname = onlineClass.getInstructorId().getInstructorNickname();
		this.onlineClassPrice = df.format(onlineClass.getOnlineClassPrice());
		this.category = onlineClass.getOnlineCategoryId().getOnlineCategoryContent();
	}
	
}
