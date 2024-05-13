package com.sist.nbgb.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OfflineClassPaymentListDTO;
import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.dto.OnlineReviewCommentDTO;
import com.sist.nbgb.dto.ReviewDTO;
import com.sist.nbgb.dto.ReviewUpdateDTO;
import com.sist.nbgb.dto.ReviewRequestDTO;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlineReviewCommentRepository;
import com.sist.nbgb.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final OnlineClassRepository onlineClassRepository;
	private final OnlineReviewCommentRepository onlineReviewCommentRepository;
	private final ReviewRepository reviewRepository;
	private final OfflineRepository offlineRepository;
	
	/*마이페이지 온라인 수강목록*/
	@Transactional
	public List<OnlinePaymentClassListDTO> userOnlineLectureList(String partnerUserId){
		Sort sort = sortByDate();
		return onlineClassRepository.userOnlineLectureList(partnerUserId, sort);
	}
	private Sort sortByDate() {
	    return JpaSort.unsafe(Sort.Direction.DESC, "approvedAt + o.onlineClassPeriod");
	}
	
	/*마이페이지 오프라인 수강목록*/
	@Transactional
	public List<OfflineClassPaymentListDTO> userOfflineLectureList(String partnerUserId){
		Sort sort = sortByBooking();
		return offlineRepository.userOfflineLectureList(partnerUserId, sort);
	}
	private Sort sortByBooking() {
	    return JpaSort.unsafe(Sort.Direction.DESC, "bookingDate + p.bookingTime");
	}
	
	//강의정보
	public OfflineClass offlineInfo(Long offlineClassId){
		return offlineRepository.findByView(offlineClassId);
	}
	
	//사용자 후기 작성 여부 조회
	public int exsitsReview(String partnerOrderId) {
		return reviewRepository.countByPartnerOrderId(partnerOrderId);
	}  
	
	//사용자 후기 조회
	public boolean exsitsReview(Long reviewId) {
		return reviewRepository.existsById(reviewId);
	}
	public ReviewDTO viewReview(String partnerOrderId) {
		Review review = reviewRepository.findByPartnerOrderId(partnerOrderId);
		return new ReviewDTO(review);
	}
	
	//사용자 후기 답변 조회
	public boolean exsitsComment(Long reviewId) {
		return onlineReviewCommentRepository.existsByReviewId_reviewId(reviewId);
	}
	public OnlineReviewCommentDTO viewReviewComment(Long reviewId) {
		ReviewComment comment = onlineReviewCommentRepository.findByReviewId_reviewId(reviewId);
		OnlineReviewCommentDTO returnComment = new OnlineReviewCommentDTO(comment);
		return returnComment;
	
	}
	
	//사용자 후기 작성
	@Transactional
	public Review uploadOnlineReview(ReviewRequestDTO userReviewRequestDTO) {
		return reviewRepository.save(userReviewRequestDTO.toEntity());
	}
	
	//사용자 후기 수정
	@Transactional
	public Review updateReview(Long reviewId, ReviewUpdateDTO reviewUpdateDTO) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such Review"));
		review.update(reviewUpdateDTO.getReviewContent(), reviewUpdateDTO.getReviewRating(), reviewUpdateDTO.getReviewRegdate());
		return review;
	}
	
	//사용자 후기 삭제
	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}
}
