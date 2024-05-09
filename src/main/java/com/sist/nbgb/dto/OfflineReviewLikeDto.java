package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ReviewLike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflineReviewLikeDto 
{
	private Long reviewId;
	
	private String userId;
	
	private int code;
	
	public static OfflineReviewLikeDto toDto(ReviewLike like)
	{
		return OfflineReviewLikeDto.builder()
				.reviewId(like.getReviewId().getReviewId())
				.userId(like.getUserId().getUserId())
				.build();
	}
}
