package com.sist.nbgb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.repository.OnlineReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineReviewService {
	private final OnlineReviewRepository reviewRepository;
	
	//후기 목록 조회
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}
}
