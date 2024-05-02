package com.sist.nbgb.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OnlineClassFileResponseDTO;
import com.sist.nbgb.dto.OnlineClassLogDTO;
import com.sist.nbgb.dto.OnlineClassLogIdDTO;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLogId;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.OnlineClassPlayService;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OnlineClassPlayController 
{
	private final Logger logger = LoggerFactory.getLogger(OnlineClassPlayController.class);
	
	private final OnlineClassService onlineClassService;
	private final OnlineClassPlayService onlineClassPlayService;
	
	//온라인 강의 재생 페이지
	@GetMapping("onlinePlay")
	public String onlinePlay(Model model, 
							@RequestParam(value="classId", required=false, defaultValue="22") Long onlineClassId,
							@RequestParam(value="fileId", required=false, defaultValue="1") Long onlineFileId) {
		//logger.debug("강의 번호 : " + onlineClassId + ", 강의 회차 : " + onlineFileId);
		/* Principal principal, */
		//마이페이지에서 classId 가져오기랑 결제 정보 등 비교
		//로그 최신 순에서 fileId가져오기 - 온라인 강의 view 페이지에서 테스트
		//회원 아이디 조회
		//String userId = principal.getName();
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
		System.out.println("))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
		//강의 로그 없으면 insert
		/*OnlineClassLogIdDTO logCheckId = OnlineClassLogIdDTO.builder().userId(userId).onlineClassId(onlineClassId).onlineFileId(onlineFileId).build();
		if(onlineClassPlayService.logCheck(logCheckId.toEntity()) == null){
			OnlineClassLogDTO firstLog = OnlineClassLogDTO.builder()
										.onlineClassLogId(OnlineClassLogId.builder().userId(userId).OnlineClassFileId(classFile.getOnlineClassFileId()).build())
										.onlineClassFile(classFile)
										.onlineLogCurr(Long.valueOf(0))
										.status(Status.N)
										.build();
			onlineClassPlayService.firstLogSave(firstLog);
			logger.debug("======강의 로그 : 아이디 = " + userId + ", 강의번호 = " + onlineClass.getOnlineClassId() + ", 강의회차 = " + classFile.getOnlineClassFileId());
			logger.debug("======강의 로그 상세 : 영상 총 길이 = " + classFile.getOnlineClassFileId().getOnlineFileLength() + ", 현재 시간 = " + LocalDateTime.now());
		}*/
		//logiddto랑 logdto 레퍼지토리랑 서비스 수정해야 함
		
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("classList", classList);
		model.addAttribute("classFile", new OnlineClassFileResponseDTO(classFile));
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("videoFileName", videoFileName);
		return "/onlineClass/onlineClassPlay";
	}
	
	
	//온라인 강의 로그 추가(수강시작)
	/*@ResponseBody
	@PostMapping("onlinePlay")
	public void onlinePlayLog(Principal principal,
										@RequestParam Map<String, Object> param){
		
		String id = (String)param.get("onlineClassId");
		System.out.println("id : " + id + "==================");
		
		
	}*/

}
