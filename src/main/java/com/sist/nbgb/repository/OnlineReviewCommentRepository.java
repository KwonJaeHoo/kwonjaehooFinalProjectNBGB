package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.Review;


public interface OnlineReviewCommentRepository extends JpaRepository<ReviewComment, Long>{
	ReviewComment findByReviewId(Review reviewId);
	//후기 댓글 목록
	@Query("SELECT a FROM ReviewComment a WHERE a.id.reviewId "
	 		+ "IN (SELECT r.reviewId FROM Review r "
	 		+ "WHERE r.classId = :onlineClassId AND r.classIden = 'ON') ")
	List<ReviewComment> findOnlineComment(@Param("onlineClassId") Long onlineClassId);
		
}
