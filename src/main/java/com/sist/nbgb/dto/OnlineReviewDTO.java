package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
public class OnlineReviewDTO {
	private Long reviewId;
	private Long classId;
	private String classIden;
	private Long reviewRating;
	private String reviewContent;
	private User userId;
	private String userNickname;
	private LocalDateTime reviewRegdate;
	private Status reviewStatus;
	private Long reviewLikeCnt;
	
	public OnlineReviewDTO(Review review) {
		this.reviewId = review.getReviewId().getReviewId();
		this.classId = review.getClassId();
		this.classIden = review.getClassIden();
		this.reviewRating = review.getReviewRating();
		this.reviewContent = review.getReviewContent();
		this.userId = review.getUserId();
		this.userNickname = review.getUserId().getUserNickname();
		this.reviewRegdate = review.getReviewRegdate();
		this.reviewStatus = review.getReviewStatus();
		this.reviewLikeCnt = review.getReviewLikeCnt();
	}
	
	
}
