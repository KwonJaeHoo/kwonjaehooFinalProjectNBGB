package com.sist.nbgb.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OfflineClassPaymentListDTO;
import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.dto.OnlineReviewCommentDTO;
import com.sist.nbgb.dto.ReviewDTO;
<<<<<<< Updated upstream
=======
import com.sist.nbgb.dto.ReviewReportDTO;
import com.sist.nbgb.dto.ReviewUpdateDTO;
>>>>>>> Stashed changes
import com.sist.nbgb.dto.ReviewRequestDTO;
import com.sist.nbgb.dto.ReviewUpdateDTO;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.ReviewReportId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlineReviewCommentRepository;
import com.sist.nbgb.repository.ReviewReportRepository;
import com.sist.nbgb.repository.ReviewRepository;
import com.sist.nbgb.repository.UserRepository;

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
<<<<<<< Updated upstream
	private final UserRepository userRepository;
=======
	private final ReviewReportRepository reportRepository;
>>>>>>> Stashed changes
	
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
		return onlineReviewCommentRepository.existsByReviewId(reviewId);
	}
	public OnlineReviewCommentDTO viewReviewComment(Long reviewId) {
		ReviewComment comment = onlineReviewCommentRepository.findByReviewId(reviewId);
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
<<<<<<< Updated upstream

	//후기 포인트 지급 및 회수
	@Transactional
	public int payPoint(String userId, Long point)
	{
		return userRepository.payPoint(userId, point);
	}
	@Transactional
	public int returnPoint(String userId, Long point)
	{
		return userRepository.returnPoint(userId, point);
	}
=======
	
	//후기 신고
	public ReviewReport insertReport(ReviewReportDTO dto) {
		return reportRepository.save(dto.toEntity());
	}
	
	//후기 조회
	public Optional<Review> findById(Long reviewId) {
		return reviewRepository.findById(reviewId);
	}
	
	//후기 신고 조회
	public boolean existReport(Long reviewId, String userId) {
		return reportRepository.existsById_reviewIdAndId_userId(reviewId, userId);
	}
	
>>>>>>> Stashed changes
}
