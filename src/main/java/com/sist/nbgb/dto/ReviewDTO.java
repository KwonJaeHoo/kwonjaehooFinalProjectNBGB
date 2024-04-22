package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Review;

import lombok.Getter;

@Getter
public class ReviewDTO {
	private final String reviewContent;
	private final String userId;
	
	public ReviewDTO(Review review)
	{
		this.reviewContent = review.getReviewContent();
		this.userId = review.getUserId().getUserId();
	}
}
