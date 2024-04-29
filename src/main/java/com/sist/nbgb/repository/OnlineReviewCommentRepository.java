package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;

public interface OnlineReviewCommentRepository extends JpaRepository<ReviewComment, ReviewId>{
	ReviewComment findByReviewId_reviewId(Long reivewId);
	
	//후기 댓글 목록
	@Query("SELECT a FROM ReviewComment a WHERE a.id.reviewId "
	 		+ "IN (SELECT r.reviewId FROM Review r "
	 		+ "WHERE r.classId = :onlineClassId AND r.classIden = 'ON') ")
	List<ReviewComment> findOnlineComment(@Param("onlineClassId") Long onlineClassId);
		
}
