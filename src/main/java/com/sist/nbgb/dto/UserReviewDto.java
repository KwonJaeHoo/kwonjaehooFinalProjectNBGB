package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

@EqualsAndHashCode
@Builder
public class UserReviewDto
{
	private final Long classId;
	
	private final String classIden;
	
	private final Long reviewRating;

	private final String reviewContent;
	
	private final User userId;
	
	private final LocalDateTime reviewRegdate;
	
	private final Long reviewLikeCnt;
	
	public UserReviewDto(Review review)
	{
		this.classId = review.getClassId();
		this.classIden = review.getClassIden();
		this.reviewRating = review.getReviewRating();
		this.reviewContent = review.getReviewContent();
		this.userId = review.getUserId();
		this.reviewRegdate = review.getReviewRegdate();
		this.reviewLikeCnt = review.getReviewLikeCnt();
		
	}
}