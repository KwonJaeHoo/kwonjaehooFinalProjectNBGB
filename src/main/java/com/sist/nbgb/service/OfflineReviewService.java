package com.sist.nbgb.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OfflineReviewLikeDto;
import com.sist.nbgb.dto.OnlineReviewLikeDTO;
import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineReviewLikeRepository;
import com.sist.nbgb.repository.OfflineReviewReplyRepository;
import com.sist.nbgb.repository.OfflineReviewRepository;
import com.sist.nbgb.repository.ReviewLikeRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineReviewService 
{
	private final OfflineReviewRepository offlineReviewRepository;
	
	private final OfflineReviewReplyRepository replyRepository;
	
	private final ReviewLikeRepository likeRepository;
	
	private final UserRepository userRepository;
	
	private final UserService userService;
	
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
	//후기 추천 여부
	public long findReviewLikeMe(long reviewId, String userId) {
		return likeRepository.countByReviewLikeId_reviewIdAndReviewLikeId_userId(reviewId, userId);
	}
	
	//후기 추천 수 증가
	@Transactional
	public int updateReviewLike(long reviewId) {
		Long likeCnt = likeRepository.countByReviewLikeId_reviewId(reviewId);
		
		return offlineReviewRepository.updateReviewLikeCnt(reviewId, likeCnt);
	}
	
	//후기 추천 등록
	@Transactional
	public OfflineReviewLikeDto saveReviewLike(OfflineReviewLikeDto likeDto) {
		
		User user = userRepository.findFirstByUserId(likeDto.getUserId()); //아이디 받아오기
		
		likeRepository.insertReviewLike(likeDto.getReviewId(), user.getUserId());
		
		return likeDto;
	}
	
	//후기 작성자 이미지
	public String getImg(String userId) {
		String img = "N";
		UserInfoDto userInfoDto = userService.findByUserId(userId);
    	
		String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
	    String filename = userInfoDto.getUserId() + ".png"; // 기본 파일명
	    String filepath = path + "/" + filename;
		
        File file = new File(filepath);
        
        if(file.exists())
		{
        	img = "Y";
		}
        
        return img;
	}
}