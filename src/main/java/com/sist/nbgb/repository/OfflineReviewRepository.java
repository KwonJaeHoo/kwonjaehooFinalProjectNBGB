package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

public interface OfflineReviewRepository extends JpaRepository<Review, Long>
{
	@Query("SELECT NVL(ROUND(AVG(NVL(reviewRating, 0)), 1), 0) FROM Review WHERE classId = :offlineClassId AND classIden = 'OFF'")
	float offCountRating(@Param("offlineClassId") Long offlineClassId);
	
	@Query("SELECT NVL(COUNT(reviewId), 0) FROM Review WHERE classId = :offlineClassId AND classIden = 'OFF'")
	int offCount(@Param("offlineClassId") Long offlineClassId);
	
	@Query("SELECT r, u.userNickname FROM Review r, User u WHERE r.userId = u.userId AND r.classId = :offlineClassId AND r.classIden = 'OFF'")
	List<Review> findReview(@Param("offlineClassId") Long offlineClassId);
	
	@Query("SELECT b.reviewId, a.reviewCommentContent, a.reviewCommentRegdate, a.reviewCommentStatus, a.instructorId "
			+ "FROM ReviewComment a, Review b "
			+ "WHERE a.reviewId = b.reviewId "
			+ "AND b.reviewId IN (SELECT reviewId FROM Review WHERE classId = :offlineClassId AND classIden = 'OFF')")
    List<ReviewComment> findReviewComment(@Param("offlineClassId") Long offlineClassId);
	
	//후기 목록 페이징
	Page<Review> findAllByClassIdAndClassIdenAndReviewStatus(Pageable pageable, Long classId, String classIden, Status reviewStatus);
	
	//추천 시 추천 수 증가
	@Modifying
	@Query("update Review o set o.reviewLikeCnt = o.reviewLikeCnt + 1 where o.reviewId.reviewId = :reviewId")
	int updateReviewLikeCnt(@Param("reviewId") Long reviewId);
	
	//추천 갯수 세서 증가
	@Modifying
	@Query("update Review o set o.reviewLikeCnt = :likeCnt where o.reviewId.reviewId = :reviewId")
	int updateReviewLikeCnt(@Param("reviewId") Long reviewId, @Param("likeCnt") Long likeCnt);
	
	//reviewId 가져오기
	Review findFirstByReviewId(Long reviewId);
}