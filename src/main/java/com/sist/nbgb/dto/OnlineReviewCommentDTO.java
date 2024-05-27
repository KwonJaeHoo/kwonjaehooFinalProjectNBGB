package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class OnlineReviewCommentDTO {
	private long reviewId;
	private String reviewCommentContent;
	private Instructors instructorId;
	private String instructorNickname;
	private LocalDateTime reviewCommentRegdate;
	private Status reviewCommentStatus;
   
public OnlineReviewCommentDTO(ReviewComment comment) {
    this.reviewId = comment.getReviewId();
    this.reviewCommentContent = comment.getReviewCommentContent();
    this.instructorId = comment.getInstructorId();
    this.instructorNickname = comment.getInstructorId().getInstructorNickname();
    this.reviewCommentRegdate = comment.getReviewCommentRegdate();
    this.reviewCommentStatus = comment.getReviewCommentStatus();
   }
}
