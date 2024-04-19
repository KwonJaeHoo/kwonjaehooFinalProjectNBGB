package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.Review;

public interface OnlineReviewRepository extends JpaRepository<Review, Long> {
	@Query("SELECT NVL(ROUND(AVG(NVL(reviewRating, 0)), 1), 0) FROM Review WHERE classId = :onlineClassId AND classIden = 'ON'")
	float onCountRating(@Param("onlineClassId") Long onlineClassId);
	
	@Query("SELECT NVL(COUNT(reviewId), 0) FROM Review WHERE classId = :onlineClassId AND classIden = 'ON'")
	int onCount(@Param("onlineClassId") Long onlineClassId);
}
