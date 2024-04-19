package com.sist.nbgb.service;

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

}
