package com.sist.nbgb.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.dto.OnlineClassFileResponseDTO;
import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.service.OnlineClassPlayService;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OnlineClassPlayController {
	
	private final OnlineClassService onlineClassService;
	private final OnlineClassPlayService onlineClassPlayService;
	
	//온라인 강의 재생 페이지
	@GetMapping("onlinePlay")
	public String onlinePlay(Model model,
							@RequestParam(value="classId", required=false, defaultValue="22") Long onlineClassId,
							@RequestParam(value="fileId", required=false, defaultValue="1") Long onlineFileId) {
		//마이페이지에서 classId 가져오기랑 결제 정보 등 비교
		//로그 최신 순에서 fileId가져오기
		
		//강의 아이디 조회
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		//강의 자료 조회
		OnlineClassFile classFile = onlineClassPlayService.selectClass(onlineClassId, onlineFileId);
		
		String videoFileName = classFile.getOnlineFileName();
		
		//강의 재생목록 조회
		List<OnlineClassFileResponseDTO> classList = onlineClassPlayService.selectClassList(onlineClassId)
											.stream().map(OnlineClassFileResponseDTO::new)
											.collect(Collectors.toList());
		//강의 자료 개수 조회
		Long classCnt = onlineClassPlayService.countClass(onlineClassId);
		
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("classList", classList);
		model.addAttribute("classFile", new OnlineClassFileResponseDTO(classFile));
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("videoFileName", videoFileName);
		return "/onlineClass/onlineClassPlay";
	}
	
	
	//온라인 강의 로그 추가(수강시작)
	@PostMapping("onlinePlay")
	public ResponseEntity<OnlineClassLog> onlinePlayLog(@RequestParam OnlineClassLogDTO dto, HttpServletRequest httpServletRequest){
		
		//세션 사용자 정보 가져오기
		
		
		
		OnlineClassLog onlineClasslogadded = onlineClassPlayService.addLog(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(onlineClasslogadded);
	}

}
