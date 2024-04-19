package com.sist.nbgb.controller;

import java.time.LocalDateTime;

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
	
	//온라인 강의 상세 조회
	@GetMapping("/online/{onlineClassId}")
	public String onlineView(@PathVariable Long onlineClassId, Model model) {
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		
		//결제 시간
		String str = String.valueOf(onlineClassId);
		//쿠키로 현재 로그인 한 id 가져와야함
		LocalDateTime approvedAt = onlineClassService.findApproveAt(str, "sist1");
		//강의 수강 기간
		long period = onlineClass.getOnlineClassPeriod();
		//강의 들을 수 있는 날짜
		LocalDateTime endDate = approvedAt.plusDays(period);
		//결제 여부(기본 N, 결제 완료되어 수강 할 수 있는 상태면 Y)
		String payStatus = "N";
		
		//좋아요 수
		long likeCnt = onlineClassService.findLikeCnt(onlineClassId);
		//내가 좋아요 상태
		String likeStatus = "N";
		
		//쿠키아이디가져와야됨
		if(onlineClassService.findLikeMe(onlineClassId, "on", "sist1") > 0) {
			likeStatus = "Y";
		}
		
		log.info("approvedAt"+approvedAt);
		log.info("period"+period);
		log.info("endDate"+endDate);
		
		if(LocalDateTime.now().isBefore(endDate) || LocalDateTime.now().isEqual(endDate)) {
			payStatus = "Y";
		}
		
		//조회수 증가
		onlineClassService.updateViews(onlineClassId);
		
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("onlineClassId", onlineClassId);
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("payStatus", payStatus);
		model.addAttribute("likeStatus", likeStatus);
		
		return "onlineClass/onlineClassView";
	}

	
}
