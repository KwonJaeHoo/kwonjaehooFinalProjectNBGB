package com.sist.nbgb.videoTest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;

public interface OnlineClassFileTestRepository extends JpaRepository<OnlineClassFile, OnlineClassFileId>{
	OnlineClassFile findByOnlineFileName(String onlineFileName);

	OnlineClassFile save(OnlineClassFileTestDTO dto);
}
