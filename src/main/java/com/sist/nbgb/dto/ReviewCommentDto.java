package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReviewCommentDto
{
	private Long reviewId;
	
	private String reviewCommentContent;
	
	private String instructorId;
	
	public static ReviewCommentDto toDto(ReviewComment comment)
	{
		return ReviewCommentDto.builder()
				.reviewId(comment.getReviewId())
				.reviewCommentContent(comment.getReviewCommentContent())
				.instructorId(comment.getInstructorId().getInstructorId())
				.build();
	}
}
