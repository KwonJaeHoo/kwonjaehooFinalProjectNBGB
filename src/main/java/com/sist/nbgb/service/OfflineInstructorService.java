package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.ReviewCommentDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflinePaymentApproveRepository;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OfflineReviewReplyRepository;
import com.sist.nbgb.repository.OfflineReviewRepository;
import com.sist.nbgb.repository.OfflineUserRepository;
import com.sist.nbgb.repository.OnlineClassRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineInstructorService 
{
	private final OfflineRepository offlineRepository;
	private final InstructorsRepository instrucRepostiory;
	private final OfflinePaymentApproveRepository offPayRepository;
	private final OfflineUserRepository userRepository;
	private final OfflineReviewRepository reviewRepository;
	private final OnlineClassRepository onlineRepository;
	private final OfflineReviewReplyRepository commentRepository;
	
	//목록 불러오기
	public List<OfflineClass> findByInstructorIdDesc(String id)
	{
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findByInstructorIdOrderByOfflineClassIdDesc(instructorId);
	}
	
	//목록 페이징
	public Page<OfflineClass> findByInstructorId(int page, String id)
	{
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OfflineClassId"));
		
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findByInstructorId(pageable, instructorId);
	}
	
	//마이페이지 페이징 - 결제
	public List<OfflineClass> offlinePayInstructor(int page, String id, Status onlineClassApprove)
	{
//		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OfflineClassId"));
		
//		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findInsList(id);
	}
	
	//시간 리스트 가져오기
	public ArrayList<String> timeList(String offlineClassId, String date)
	{
		return offPayRepository.timeList(offlineClassId, date);
	}
	
	//예약한 사람 가져오기
	public List<User> payList(String date, String time, String id)
	{
		return userRepository.findList(id, date, time);
	}
	
	//후기 리스트 가져오기
	public List<Review> insReview(String instructorId)
	{
		return reviewRepository.insReviewList(instructorId);
	}
	
	public List<OnlineClass> findByInstuctorId(String id)
	{
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return onlineRepository.findByInstructorIdOrderByOnlineClassIdDesc(instructorId);
	}
	
	//마이페이지 - 온라인 상태 변경
	@Transactional
	public Object instructorOnlineLectureStatusChage(Long onlineClassId)
	{
		OnlineClass classid = onlineRepository.findByOnlineClassId(onlineClassId);
		
		Optional<OnlineClass> offlineClass = Optional.of(classid);
		
		try 
		{
			offlineClass.ifPresent(value -> value.setOnlineClassApprove(Status.B));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	public Review findByReviewId(Long reviewId)
	{
		return reviewRepository.findByReviewId(reviewId);
	}
	
	//강사 후기 답변 등록
	@Transactional
	public ReviewCommentDto ReviewCommentPost(ReviewCommentDto reviewCommentDto)
	{
		Review reviewId = reviewRepository.findByReviewId(reviewCommentDto.getReviewId());
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(reviewCommentDto.getInstructorId());
		
		System.out.println("review : " + reviewCommentDto.getInstructorId());
		System.out.println("review : " + reviewCommentDto.getReviewCommentContent());
		System.out.println("review : " + reviewCommentDto.getReviewId());
		
		ReviewComment reviewComment = ReviewComment.builder()
				.reviewId(reviewId.getReviewId())
				.reviewCommentContent(reviewCommentDto.getReviewCommentContent())
				.instructorId(instructorId)
				.reviewCommentStatus(Status.Y)
				.reviewCommentRegdate(LocalDateTime.now())
				.build();
		
		return ReviewCommentDto.toDto(commentRepository.save(reviewComment));
	}
	
	public Long countByReviewId(Long reivewId)
	{
		return commentRepository.countByReviewId(reivewId);
	}
	
	//강사 후기 답변 수정
	@Transactional
	public int updateComment(ReviewCommentDto reviewCommentDto)
	{				
		return commentRepository.updateComment(reviewCommentDto.getReviewCommentContent(), reviewCommentDto.getReviewId());				
	}
}
