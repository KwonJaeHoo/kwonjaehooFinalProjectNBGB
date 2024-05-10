package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> 
{
	List<Review> findAllByUserId(User user);
}