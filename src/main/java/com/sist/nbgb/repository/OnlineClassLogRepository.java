package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;

public interface OnlineClassLogRepository extends JpaRepository<OnlineClassLog, OnlineClassLogId> {
		
}
