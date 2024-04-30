package com.sist.nbgb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OfflineReviewLikeDto;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineReviewLikeRepository;
import com.sist.nbgb.repository.OfflineReviewReplyRepository;
import com.sist.nbgb.repository.OfflineReviewRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineReviewService 
{
	private final OfflineReviewRepository offlineReviewRepository;
	
	private final OfflineReviewReplyRepository replyRepository;
	
	private final OfflineReviewLikeRepository likeRepository;
	
	private final UserRepository userRepository2;
	
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
	
	//리뷰 좋아요
	@Transactional
	public OfflineReviewLikeDto reviewLike(OfflineReviewLikeDto like)
	{
		Review reviewId = offlineReviewRepository.findFirstByReviewId(like.getReviewId());
		
		User user = userRepository2.findFirstByUserId(like.getUserId());
		
		ReviewLike reviewLike1 = ReviewLike.builder()
				.reviewId(reviewId)
				.userId(user)
				.build();
		
		return OfflineReviewLikeDto.toDto(likeRepository.save(reviewLike1));
	}
	
	//리뷰 중복 검색
	public Long duplicationReviewLike(Long reviewId, String userId)
	{
		return likeRepository.countByReviewId_ReviewIdAndAndReviewId_userId(reviewId, userId);
	}
}