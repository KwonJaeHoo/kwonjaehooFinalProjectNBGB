package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewId;

import lombok.Getter;

@Getter
public class ReviewDTO {
	private final ReviewId reviewId;
	private final String reviewContent;
	private final String userId;
	private final String userNickname;
	
	public ReviewDTO(Review review)
	{
		this.reviewId = review.getReviewId();
		this.reviewContent = review.getReviewContent();
		this.userId = review.getUserId().getUserId();
		this.userNickname = review.getUserId().getUserNickname();
	}
}
