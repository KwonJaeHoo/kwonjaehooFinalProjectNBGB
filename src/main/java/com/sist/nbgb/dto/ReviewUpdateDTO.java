package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ReviewUpdateDTO {
	private String reviewContent;
	private Long reviewRating;
	private LocalDateTime reviewRegdate;
	
	public ReviewUpdateDTO(String reviewContent, Long reviewRating) {
		this.reviewContent = reviewContent;
		this.reviewRating = reviewRating;
		this.reviewRegdate = LocalDateTime.now().withNano(0);
	}
}
