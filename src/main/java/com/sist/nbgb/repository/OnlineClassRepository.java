package com.sist.nbgb.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;


public interface OnlineClassRepository extends JpaRepository<OnlineClass, Long> {
	//전체 리스트 조회(최신순)
	List<OnlineClass> findByOnlineClassApproveOrderByOnlineClassIdDesc(Status onlineClassApprove);
	
	//카테고리별 리스트 조회
	List<OnlineClass> findByOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(long onlineCategoryId, Status onlineClassApprove);
	
	//검색 기능
	List<OnlineClass> findByOnlineClassTitleContainingAndOnlineClassApprove(String searchKeyword, Status onlineClassApprove);
	
	//카테고리 내 검색
	List<OnlineClass> findByOnlineClassTitleContainingAndOnlineCategoryId_onlineCategoryIdAndOnlineClassApprove(String searchKeyword, long onlineCategoryId, Status onlineClassApprove);
	
	

}
