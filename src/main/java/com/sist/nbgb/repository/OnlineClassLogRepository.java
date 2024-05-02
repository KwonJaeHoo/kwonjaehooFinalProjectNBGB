package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.dto.OnlineClassLogIdDTO;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;



public interface OnlineClassLogRepository extends JpaRepository<OnlineClassLog, OnlineClassLogId> {
	//강의 선택 회차 로그 유무 조회
	//OnlineClassLog findByOnlineClassLogId(OnlineClassLogIdDTO dto);
	
	//강의 선택 회차 첫번째 로그
	//OnlineClassLog save(OnlineClassLogDTO dto);
}
