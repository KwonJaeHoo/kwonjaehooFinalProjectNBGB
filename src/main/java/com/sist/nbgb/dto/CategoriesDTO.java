package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OnlineCategory;

import lombok.Getter;

@Getter
public class CategoriesDTO {
	private final Long onlineCategoryId;
	private final String onlineCategoryContent;
	
	public CategoriesDTO(OnlineCategory onlineCategory) {
		this.onlineCategoryId = onlineCategory.getOnlineCategoryId();
		this.onlineCategoryContent = onlineCategory.getOnlineCategoryContent();
	}
}
