package com.sist.nbgb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineReviewReplyRepository;
import com.sist.nbgb.repository.OfflineReviewRepository;

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
	
	//후기 목록 페이징
	public Page<Review> reviewListPage(int page, Long classId, String classIden, Status reviewStatus)
	{
		Pageable pageable = PageRequest.of(page, 2, Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		
		return this.offlineReviewRepository.findAllByClassIdAndClassIdenAndReviewStatus(pageable, classId, classIden, reviewStatus);
	}
}