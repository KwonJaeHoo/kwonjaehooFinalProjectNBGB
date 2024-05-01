package com.sist.nbgb.videoTest;

import java.io.File;
import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.service.FFmpegManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VideoTestService {
	private final OnlineClassFileTestRepository fileTestRepository;
	private final FFmpegManager ffmpegManager;
	
	//파일 데이터 저장
	public OnlineClassFile saveFile(OnlineClassFileTestDTO dto) throws IOException{
        return fileTestRepository.save(dto.toEntity());
    }
	
	//썸네일 추출
	public void saveThumnail(File file) throws IOException{
		ffmpegManager.getThumbnail(file.getAbsolutePath());
	}
    
}