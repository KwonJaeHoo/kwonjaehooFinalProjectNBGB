package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OnlineClassController {
	private final OnlineClassService onlineClassService;

	//글 하나 조회
//	@GetMapping("/api/online/{onlineClassId}") //URL 경로에서 값 추출
//	public ResponseEntity<OnlineClassView> findArticle(@PathVariable long onlineClassId) {
//		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
//		
//		return ResponseEntity.ok()
//				.body(new OnlineClassView(onlineClass));
//	}
	
	@GetMapping("/online/{onlineClassId}")
	public String getArticle(@PathVariable Long onlineClassId, Model model) {
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		
		//좋아요 수
		long likeCnt = onlineClassService.findLikeCnt(onlineClassId);
		
		//결제 시간
		String str = String.valueOf(onlineClassId);
		//LocalTime approvedAt = onlineClassService.findByApprovedAt(str, "sist1");
		//쿠키로 현재 로그인 한 id 가져와야함
		
		
		
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("likeCnt", likeCnt);
		
		return "onlineClass/onlineClassView";
	}
	
	
}
