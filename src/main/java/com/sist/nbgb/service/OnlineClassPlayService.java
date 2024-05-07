package com.sist.nbgb.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.dto.OnlineClassLogIdDTO;
import com.sist.nbgb.dto.OnlineClassLogReqDTO;
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
	
	//강의 선택 회차 로그 유무 조회
	//public List<OnlineClassLog> logCheck(String userId, Long onlineClassId) {
	//	return onlineClassLogRepository.findByOnlineClassLogId_UserIdAndOnlineClassLogId_OnlineClassFileId_onlineClassId(userId, onlineClassId);
	//}
	
	//로그 저장
	public OnlineClassLog firstLogSave(OnlineClassLogDTO onlineClassLogDTO) {
		return onlineClassLogRepository.save(onlineClassLogDTO.toEntity());
	}
	
	//로그 업데이트
	@Transactional
	public OnlineClassLog logUpdate(OnlineClassLogIdDTO onlineClassLogIdDTO, OnlineClassLogReqDTO onlineClassLogReqDTO) {
		OnlineClassLog onlineClassLog = onlineClassLogRepository.findByOnlineClassLogId(onlineClassLogIdDTO.toEntity());
		onlineClassLog.update(onlineClassLogReqDTO.getOnlineLogCurr(), onlineClassLogReqDTO.getOnlineLogDate(), onlineClassLogReqDTO.getStatus());
		return onlineClassLog;
	}
	
	//로그 유무 조회
	public boolean logCheck(OnlineClassLogIdDTO onlineClassLogIdDTO) {
		return onlineClassLogRepository.countByOnlineClassLogId(onlineClassLogIdDTO.toEntity());
	}
}
