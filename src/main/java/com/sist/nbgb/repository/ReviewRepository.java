package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

public interface ReviewRepository extends JpaRepository<Review, Long> 
{
	List<Review> findAllByUserId(User user);
	
	//사용자 리뷰 조회
	Review findByPartnerOrderId(String partnerOrderId);
	
	//사용자 리뷰 작성 여부
	int countByPartnerOrderId(@Param("partnerOrderId") String partnerOrderId);
	
	Review findByReviewId(Long reviewId);
	
	@Query("select r.reviewStatus from Review r where r.reviewId = :reviewId")
	Status reviewStatusCheck(@Param("reviewId")Long reviewId);
	
	@Modifying
	@Query("update Review r set r.reviewStatus = :reviewStatus where r.reviewId = :reviewId")
	int reviewStatusUpdate(@Param("reviewId")Long reviewId, @Param("reviewStatus")Status reviewStatus);
}