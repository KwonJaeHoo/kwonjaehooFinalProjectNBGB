package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> 
{
	List<Review> findAllByUserId(User user);
	
	//사용자 리뷰 조회
	Review findByPartnerOrderId(String partnerOrderId);
	
	//사용자 리뷰 작성 여부
	boolean existsByPartnerOrderId(@Param("partnerOrderId") String partnerOrderId);
}