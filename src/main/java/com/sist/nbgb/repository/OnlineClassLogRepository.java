package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;
import com.sist.nbgb.entity.User;

public interface OnlineClassLogRepository extends JpaRepository<OnlineClassLog, OnlineClassLogId> {
	//강의 사용자 로그 리스트 조회
	List<OnlineClassLog> findByUserIdAndOnlineClassLogId_onlineClassFileId_onlineClassId(User userId, Long onlineClassId);
	
	//강의 선택 회차 첫번째 로그 저장
	@SuppressWarnings("unchecked")
	OnlineClassLog save(OnlineClassLog onlineClassLog);
	
	//강의 선택 회차 로그 조회
	OnlineClassLog findByOnlineClassLogId(OnlineClassLogId onlineClassLogId);
	
	//강의 선택 회차 로그 유무
	boolean countByOnlineClassLogId(OnlineClassLogId onlineClassLogId);
	
}
