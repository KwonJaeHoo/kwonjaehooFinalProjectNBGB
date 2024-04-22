package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>
{
	
}
