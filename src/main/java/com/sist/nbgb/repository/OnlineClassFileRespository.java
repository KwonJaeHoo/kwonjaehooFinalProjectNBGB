package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;

public interface OnlineClassFileRespository extends JpaRepository<OnlineClassFile, OnlineClassFileId>{
	Long countByOnlineClassFileId_onlineClassId(Long onlineClassId);
}
