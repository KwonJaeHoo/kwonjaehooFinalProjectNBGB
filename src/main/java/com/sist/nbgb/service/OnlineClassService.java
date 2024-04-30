package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OfflinePostDto;
import com.sist.nbgb.dto.OnlinePostDTO;
import com.sist.nbgb.dto.OnlineReviewLikeDTO;
import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineCategory;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.ReviewLikeId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.ClassLikeRepository;
import com.sist.nbgb.repository.OnlineCategoryRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlinePaymentApproveRepository;
import com.sist.nbgb.repository.OnlineReviewCommentRepository;
import com.sist.nbgb.repository.OnlineReviewRepository;
import com.sist.nbgb.repository.ReviewLikeRepository;
import com.sist.nbgb.repository.UserRepository;

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
	private final UserRepository userRepository;
	private final ReviewLikeRepository reviewLikeRepository;
	
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
	
	//게시글 찜 등록
	@Transactional
	public ClassLikeDTO saveLike(ClassLikeDTO likeDto) {
		OnlineClass tmpClass = onlineClassRepository.findFirstByonlineClassId(likeDto.getClassId());
		ClassId classId = ClassId.builder()
				.classId(tmpClass.getOnlineClassId())
				.classIden("on")
				.build();
		User user = userRepository.findFirstByUserId("sist1"); //아이디 받아오기
		
		ClassLike like = ClassLike.builder()
				.classId(classId)
				.userId(user)
				.build();
		
		classLikeRepository.save(like);
		
		return likeDto;
	}
	
	//게시글 찜 취소
	@Transactional
	public int removeLike(ClassLikeDTO likeDto) {
		if(findLikeMe(likeDto.getClassId(), likeDto.getClassIden(),likeDto.getUserId()) >= 1) {
			return classLikeRepository.deleteByClassId_classIdAndClassId_classIdenAndClassId_userId(likeDto.getClassId(), likeDto.getClassIden(), likeDto.getUserId());	
		}
		
		return 0;
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
	
	//후기 추천 여부
	public long findReviewLikeMe(long reviewId, String userId) {
		return reviewLikeRepository.countByReviewLikeId_reviewIdAndReviewLikeId_userId(reviewId, userId);
	}
	
	//후기 추천 수 증가
	@Transactional
	public int updateReviewLike(long reviewId) {
		return reviewRepository.updateReviewLikeCnt(reviewId);
	}
	
	//후기 추천 등록
	@Transactional
	public OnlineReviewLikeDTO saveReviewLike(OnlineReviewLikeDTO likeDto) {
		log.info("likeDto.getReviewId()" + likeDto.getReviewId());
		
		User user = userRepository.findFirstByUserId("sist1"); //아이디 받아오기
		
		reviewLikeRepository.insertReviewLike(likeDto.getReviewId(), user.getUserId());
		
		return likeDto;
	}
	
	//후기 댓글 목록
	public List<ReviewComment> findOnlineComment(@Param("onlineClassId") Long onlineClassId){
		return reviewCommentRepository.findOnlineComment(onlineClassId);
	}
	
	//온라인 클래스 등록
	@Transactional
	public OnlinePostDTO onlinePost(OnlinePostDTO onlinePostDto) {
		Instructors id = findById((long) 8).getInstructorId(); //아이디 받아오기
		//카테고리 id로 카테고리 검색
		OnlineCategory cate = onlineCategoryRepository.findFirstByOnlineCategoryId(onlinePostDto.getOnlineCategoryId());
		System.out.println("333333333333333333333333333 " + cate);
		System.out.println("333333333333333333333333333 " + cate.getOnlineCategoryId());;
		
		OnlineClass onlineClass = OnlineClass.builder()
				.onlineClassTitle(onlinePostDto.getOnlineClassTitle())
				.onlineClassContent(onlinePostDto.getOnlineClassContent())
				.onlineClassRegdate(LocalDateTime.now())
				.onlineCategoryId(cate)
				.instructorId(id)
				.onlineClassPrice(onlinePostDto.getOnlineClassPrice())
				.onlineClassPeriod(onlinePostDto.getOnlineClassPeriod())
				.onlineClassApprove(Status.Y)
				.rejection(null)
				.onlineClassViews((long) 0)
				.build();
		
		System.out.println("333333333333333333333333333");
		
		 // 저장 후에 onlineClassId 값을 가져옵니다.
	    OnlineClass savedOnlineClass = onlineClassRepository.save(onlineClass);
	    Long onlineClassId = savedOnlineClass.getOnlineClassId();
	    
	    System.out.println("4444444444444444444444444444");

	    // 반환할 DTO에 onlineClassId 값을 설정한 후에 반환합니다.
	    onlinePostDto.setOnlineClassId(onlineClassId);
	    
	    System.out.println("5555555555555555555555555555");
	    
	    return onlinePostDto;
	}
}
	
