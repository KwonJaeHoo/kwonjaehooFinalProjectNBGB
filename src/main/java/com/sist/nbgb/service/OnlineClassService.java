package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.OnlineCategory;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.ClassLikeRepository;
import com.sist.nbgb.repository.OnlineCategoryRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlinePaymentApproveRepository;

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


	public List<OnlineCategory> categoryFind() {	//카테고리명 조회
		return onlineCategoryRepository.findAll();
	}

	public List<OnlineClass> findAll(Status onlineClassApprove){		//온라인 클래스 리스트
		return onlineClassRepository.findByOnlineClassApproveOrderByOnlineClassIdDesc(onlineClassApprove);
	}
	
	public List<OnlineClass> findCategoryList(long onlineCategoryId, Status onlineClassApprove){		//카테고리별 조회
		return onlineClassRepository.findByOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(onlineCategoryId, onlineClassApprove);
	}
	
	public List<OnlineClass> findSearchList(String searchKeyword, Status onlineClassApprove){	//검색기능
		return onlineClassRepository.findByOnlineClassTitleContainingAndOnlineClassApprove(searchKeyword, onlineClassApprove);
	}
	
	public List<OnlineClass> findCategorySearchList( String searchKeyword, long onlineCategoryId, Status onlineClassApprove){	//카테고리 내 검색 
		return onlineClassRepository
				.findByOnlineClassTitleContainingAndOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(searchKeyword, onlineCategoryId, onlineClassApprove);
	}
	
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


}
	
