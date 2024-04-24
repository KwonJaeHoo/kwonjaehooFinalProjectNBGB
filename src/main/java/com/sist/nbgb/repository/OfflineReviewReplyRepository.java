package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;

public interface OfflineReviewReplyRepository extends JpaRepository<ReviewComment, ReviewId> {
	 @Query("SELECT a FROM ReviewComment a WHERE a.id.reviewId IN (SELECT r.reviewId FROM Review r WHERE r.classId = :offlineClassId AND r.classIden = 'OFF')")
	 List<ReviewComment> findReviewComment(@Param("offlineClassId") Long offlineClassId);
}