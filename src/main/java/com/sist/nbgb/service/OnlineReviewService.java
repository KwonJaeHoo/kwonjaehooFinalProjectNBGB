package com.sist.nbgb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.UserReviewRequestDTO;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.repository.OnlineReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineReviewService {

	private final OnlineReviewRepository onlineReviewRepository;
	
	//사용자 리뷰 작성 여부 조회
	public int exsitsOnlineReview(User userId, Long classId, String classIden) {
		return onlineReviewRepository.countByUserIdAndClassIdAndClassIden(userId, classId, classIden);
	}
	
	//사용자 리뷰 작성
	@Transactional
	public Review uploadOnlineReview(UserReviewRequestDTO userReviewRequestDTO) {
		return onlineReviewRepository.save(userReviewRequestDTO.toEntity());
	}
}
