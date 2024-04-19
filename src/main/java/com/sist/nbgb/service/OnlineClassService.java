package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.repository.ClassLikeRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlinePaymentApproveRepository;
import com.sist.nbgb.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineClassService {
	private final OnlineClassRepository onlineClassRepository;
	private final ClassLikeRepository classLikeRepository;
	private final OnlinePaymentApproveRepository onlinePaymentApproveRepository;
	private final ReviewRepository reviewRepository;
	
	//view
	public OnlineClass findById(long onlineClassId) {
		return onlineClassRepository.findById(onlineClassId)
			.orElseThrow(() -> new IllegalArgumentException("not found: " + onlineClassId));
	}
	
	//조회수 증가
	@Transactional
	public int updateViews(long onlineClassId) {
		return onlineClassRepository.updateViews(onlineClassId);
		
	}
		
	//좋아요 수 조회
	public long findLikeCnt(long onlineClassId) {
		return classLikeRepository.countByClassId_classId(onlineClassId);
	}
	
	//내가 좋아요 했는지 확인
	public long findLikeMe(Long classId, String classIden, String userId) {
		return classLikeRepository.countByClassId_classIdAndClassId_classIdenAndClassId_userId(classId, classIden, userId);
	}
	
	//결제 날짜
	public LocalDateTime findApproveAt(String itemCode, String partnerUserId) {
		return onlinePaymentApproveRepository.findApproveAt(itemCode, partnerUserId);
	}
	
	//후기 목록 조회
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}
	
}
	
