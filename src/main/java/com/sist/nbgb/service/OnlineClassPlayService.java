package com.sist.nbgb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.repository.OnlineClassFileRepository;
import com.sist.nbgb.repository.OnlineClassLogRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class OnlineClassPlayService {
	private final OnlineClassLogRepository onlineClassLogRepository;
	private final OnlineClassFileRepository onlineClassFileRepository;
	
	//강의 자료 조회
	public OnlineClassFile selectClass(Long onlineClassId, Long onlineFileId) {
		return onlineClassFileRepository.findByOnlineClassFileIdOnlineClassIdAndOnlineClassFileIdOnlineFileId(onlineClassId, onlineFileId);
	}
	
	//강의 자료 개수 조회
	public Long countClass(Long onlineClassId) {
		return onlineClassFileRepository.countByOnlineClassFileIdOnlineClassId(onlineClassId);
	}
	
	//강의 자료 리스트 전체 조회
	public List<OnlineClassFile> selectClassList(Long onlineClassId) {
		return onlineClassFileRepository.findAllByOnlineClassFileIdOnlineClassIdOrderByOnlineFileName(onlineClassId);
	}

	//로그 저장
	public OnlineClassLog addLog(OnlineClassLogDTO dto) {
		return onlineClassLogRepository.save(dto.toEntity());
	}
}
