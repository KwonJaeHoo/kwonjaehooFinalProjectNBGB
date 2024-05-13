package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewRequestDTO {
	private Long classId;
	private String classIden;
	private String reviewContent;
	private Long reviewLikeCnt;
	private Long reviewRating;
	private LocalDateTime reviewRegdate;
    private Status reviewStatus;
	private User userId;
	private String partnerOrderId;

	public Review toEntity() {
		return Review.builder()
				.classId(classId)
				.classIden(classIden)
				.reviewContent(reviewContent)
				.reviewLikeCnt(reviewLikeCnt)
				.reviewRating(reviewRating)
				.reviewRegdate(reviewRegdate)
				.reviewStatus(reviewStatus)
				.userId(userId)
				.partnerOrderId(partnerOrderId)
				.build();
	}

}
