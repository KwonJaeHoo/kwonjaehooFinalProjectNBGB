package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.ClassLike;
import com.sist.nbgb.entity.OnlineCategory;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.ClassLikeRepository;
import com.sist.nbgb.repository.OnlineCategoryRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlinePaymentApproveRepository;
import com.sist.nbgb.repository.OnlineReviewCommentRepository;
import com.sist.nbgb.repository.OnlineReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineClassService {
	private final OnlineClassRepository onlineClassRepository;
	private final OnlineCategoryRepository onlineCategoryRepository;
	private final ClassLikeRepository classLikeRepository;
	private final OnlinePaymentApproveRepository onlinePaymentApproveRepository;
	private final OnlineReviewRepository reviewRepository;
	private final OnlineReviewCommentRepository reviewCommentRepository;
	
	/*온라인클래스 리스트*/
	//카테고리명 조회
	public List<OnlineCategory> categoryFind() {	
		return onlineCategoryRepository.findAll();
	}
	
	//온라인 클래스 리스트
	public List<OnlineClass> findAll(Status onlineClassApprove){		
		return onlineClassRepository.findByOnlineClassApproveOrderByOnlineClassIdDesc(onlineClassApprove);
	}
	
	//카테고리별 조회
	public List<OnlineClass> findCategoryList(long onlineCategoryId, Status onlineClassApprove){		
		return onlineClassRepository.findByOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(onlineCategoryId, onlineClassApprove);
	}
	
	//검색기능
	public List<OnlineClass> findSearchList(String searchKeyword, Status onlineClassApprove){	
		return onlineClassRepository.findByOnlineClassTitleContainingAndOnlineClassApprove(searchKeyword, onlineClassApprove);
	}
	
	//카테고리 내 검색 
	public List<OnlineClass> findCategorySearchList( String searchKeyword, long onlineCategoryId, Status onlineClassApprove){	
		return onlineClassRepository
				.findByOnlineClassTitleContainingAndOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(searchKeyword, onlineCategoryId, onlineClassApprove);
	}
	
	//정렬기준 - 인기순(조회순)
	public List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassViews(Status onlineClassApprove){
		return onlineClassRepository.findByOnlineClassApproveOrderByOnlineClassViews(onlineClassApprove);
	}

	//정렬기준 - 가격 낮은 순
	public List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassPriceAsc(Status onlineClassApprove){
		return onlineClassRepository.findByOnlineClassApproveOrderByOnlineClassPriceAsc(onlineClassApprove);
	}
	
	//정렬기준 - 가격 높은 순
	public List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassPriceDesc(Status onlineClassApprove){
		return onlineClassRepository
				.findByOnlineClassApproveOrderByOnlineClassPriceDesc(onlineClassApprove);
	}
	
	
	
	/*온라인클래스 상세페이지*/
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
	
	//게시글 찜 추가
	public ClassLike saveLike(ClassLike classLike) {
		validateDuplicateClassLike(classLike);
		return classLikeRepository.save(classLike);
	}
	
	//게시글 찜 여부
	public void validateDuplicateClassLike(ClassLike classLike) {
		ClassLike findLike = classLikeRepository.findByClassId_classIdAndClassId_classIdenAndClassId_userId
				(classLike.getClassId().getClassId(), classLike.getClassId().getClassIden(), classLike.getUserId().getUserId());
		if(findLike != null) {
			throw new IllegalStateException("이미 찜 목록에 담겨있습니다.");
		}
	}
	
	
	//결제 날짜
	public LocalDateTime findApproveAt(String itemCode, String partnerUserId) {
		return onlinePaymentApproveRepository.findApproveAt(itemCode, partnerUserId);		
	}

	//후기 목록 조회
	public List<Review> findOnReview(Long classId, String classIden, Status reviewStatus) {
		return reviewRepository.findByClassIdAndClassIdenAndReviewStatusOrderByReviewRegdateDesc(classId, classIden, reviewStatus);
	}
	
	//후기 개수
	public long findReviewCnt(Long classId, String classIden, Status reviewStatus) {
		return reviewRepository.countByClassIdAndClassIdenAndReviewStatus(classId, classIden, reviewStatus);
	}
	
	//후기 목록 페이징
	public Page<Review> getList(int page, Long classId, String classIden, Status reviewStatus){
		Pageable pageable = PageRequest.of(page, 2, Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		return this.reviewRepository.findAllByClassIdAndClassIdenAndReviewStatus(pageable, classId, classIden, reviewStatus);
	}
	
	//별점 평균
	public int starAvg(Long classId) {
		return reviewRepository.starAvg(classId);
	}
	
	//후기 댓글 목록
	public List<ReviewComment> findOnlineComment(@Param("onlineClassId") Long onlineClassId){
		return reviewCommentRepository.findOnlineComment(onlineClassId);
	}
	
}
	
