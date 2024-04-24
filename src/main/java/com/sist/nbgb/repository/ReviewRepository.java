package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewId;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {


}
