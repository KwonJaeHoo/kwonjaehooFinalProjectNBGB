package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.ReviewLikeId;

public interface OfflineReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId>
{
	Long countByReviewId_ReviewIdAndAndReviewId_userId(Long ReviewId, String userId);
}
