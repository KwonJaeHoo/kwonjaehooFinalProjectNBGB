package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ReviewLike;

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
public class OnlineReviewLikeDTO {
	private Long reviewId;
	private String userId;
	
	public static OnlineReviewLikeDTO toDto(ReviewLike like) {
		return OnlineReviewLikeDTO.builder()
				.reviewId(like.getReviewId().getReviewId().getReviewId())
				.userId(like.getUserId().getUserId())
				.build();
	}
}
