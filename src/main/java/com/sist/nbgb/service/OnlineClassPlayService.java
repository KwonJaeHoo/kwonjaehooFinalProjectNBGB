package com.sist.nbgb.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.dto.OnlineClassLogIdDTO;
import com.sist.nbgb.dto.OnlineClassLogReqDTO;
import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.repository.OnlineClassFileRepository;
import com.sist.nbgb.repository.OnlineClassLogRepository;
import com.sist.nbgb.repository.OnlineClassRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class OnlineClassPlayService {
	private final OnlineClassLogRepository onlineClassLogRepository;
	private final OnlineClassFileRepository onlineClassFileRepository;
	private final OnlineClassRepository onlineClassRepository;
	
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
	
	//강의 정보 및 결제 정보 조회
	public OnlinePaymentClassListDTO userLectureInfo(String partnerUserId, Long onlineClassId) {
		return onlineClassRepository.userLectureInfo(partnerUserId, onlineClassId);
	}
	
	//강의 사용자 로그 전체 조회
	public List<OnlineClassLog> selectClassLog(User user, Long onlineClassId){
		return onlineClassLogRepository.findByUserIdAndOnlineClassLogId_onlineClassFileId_onlineClassId(user, onlineClassId);
	}
	
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
