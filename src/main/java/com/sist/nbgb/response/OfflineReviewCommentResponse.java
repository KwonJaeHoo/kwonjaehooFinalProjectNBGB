package com.sist.nbgb.response;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class OfflineReviewCommentResponse 
{
	private Review reviewId;
	
	private String reviewCommentContent;
	
	private Instructors instructorId;
	
	private LocalDateTime reviewCommentRegdate;
	
	private Status reviewCommentStatus;
	
	
	public OfflineReviewCommentResponse(ReviewComment comment)
	{
		this.reviewId = comment.getReviewId();
		
		this.reviewCommentContent = comment.getReviewCommentContent();
		
		this.instructorId = comment.getInstructorId();
		
		this.reviewCommentRegdate = comment.getReviewCommentRegdate();
		
		this.reviewCommentStatus = comment.getReviewCommentStatus();
	}
}
