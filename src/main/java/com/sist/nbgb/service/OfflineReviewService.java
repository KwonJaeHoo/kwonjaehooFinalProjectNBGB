package com.sist.nbgb.service;


import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.repository.OfflineReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineReviewService 
{
	private final OfflineReviewRepository offlineReviewRepository;
	
	public float offCountRating(Long offlineClassId)
	{
		return offlineReviewRepository.offCountRating(offlineClassId);
	}
	
	public List<Review> findReview(@Param("offlineClassId") Long offlineClassId)
	{
		return offlineReviewRepository.findReview(offlineClassId);
	}
	
	public int offCount(Long offlineClassId)
	{
		return offlineReviewRepository.offCount(offlineClassId);
	}
}