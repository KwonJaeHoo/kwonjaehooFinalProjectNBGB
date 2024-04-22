package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;

public interface OfflineReviewReplyRepository extends JpaRepository<ReviewComment, ReviewId> {
    @Query("SELECT a.id.reviewId, a.reviewCommentContent, a.reviewCommentRegdate, a.reviewCommentStatus, a.instructorId FROM ReviewComment a WHERE a.id.reviewId = 6")
    List<ReviewComment> findReviewComment(@Param("offlineClassId") Long offlineClassId);
}