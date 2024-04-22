package com.sist.nbgb.service;


import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.repository.OfflineReviewReplyRepository;
import com.sist.nbgb.repository.OfflineReviewRepository;
import com.sist.nbgb.response.OfflineReviewCommentResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineReviewService 
{
	private final OfflineReviewRepository offlineReviewRepository;
	private final OfflineReviewReplyRepository replyRepository;
	
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
	
	public List<ReviewComment> findReviewComment(Long offlineClassId)
	{
		return replyRepository.findReviewComment(offlineClassId);
	}
}