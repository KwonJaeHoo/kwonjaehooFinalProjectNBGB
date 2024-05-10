package com.sist.nbgb.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OnlineReviewDTO;
import com.sist.nbgb.dto.UserReviewRequestDTO;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.repository.OnlineReviewCommentRepository;
import com.sist.nbgb.repository.OnlineReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineReviewService {

	private final OnlineReviewRepository onlineReviewRepository;
	private final OnlineReviewCommentRepository onlineReviewCommentRepository;
	
	//사용자 리뷰 작성 여부 조회
	public int exsitsOnlineReview(User userId, Long classId, String classIden) {
		//Review review = onlineReviewRepository.findByUserIdAndClassIdAndClassIden(userId, classId, classIden)		, review.getReviewRegdate();
		return onlineReviewRepository.countByUserIdAndClassIdAndClassIden(userId, classId, classIden);
		
		/* AndReviewRegdate */
	}
	
	//사용자 리뷰 조회
	public OnlineReviewDTO viewReview(User userId, Long classId, String classIden) {
		Review review = onlineReviewRepository.findByUserIdAndClassIdAndClassIden(userId, classId, classIden);
		return new OnlineReviewDTO(review);
	}
	
	//사용자 리뷰 답변 조회
	/*public OnlineReviewCommentDTO viewReviewComment(Long reviewId) {
		Optional<Review> review = onlineReviewRepository.findById(reviewId);
		ReviewComment comment = onlineReviewCommentRepository.findByReviewId_reviewId();
		OnlineReviewCommentDTO returnComment = new OnlineReviewCommentDTO(comment);
		return returnComment;
	
	}*/
	
	//사용자 리뷰 작성
	@Transactional
	public Review uploadOnlineReview(UserReviewRequestDTO userReviewRequestDTO) {
		return onlineReviewRepository.save(userReviewRequestDTO.toEntity());
	}
}
