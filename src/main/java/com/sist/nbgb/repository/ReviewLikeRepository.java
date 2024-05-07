package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.ReviewLikeId;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {	
	//내가 후기 추천 했는지 확인
	Long countByReviewLikeId_reviewIdAndReviewLikeId_userId(Long reviewId, String userId);
	
	//후기 추천
	@Modifying
	@Query(value="insert into nbgb_review_like values (:reviewId, :userId)", nativeQuery=true)
	int insertReviewLike(@Param("reviewId") Long reviewId, @Param("userId") String userId);
	
	//후기 좋아요를 위한 갯수새기
	Long countByReviewLikeId_reviewId(Long reviewId);
}
