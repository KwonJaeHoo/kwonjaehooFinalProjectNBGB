package com.sist.nbgb.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.dto.CategoriesDTO;
import com.sist.nbgb.dto.OnlineClassList;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OnlineClassController {
	private final OnlineClassService onlineClassService;
	

	//온라인 리스트 조회
	@GetMapping("/onlineClass")
	public String getAllClasses(Model model, 
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "nowCategory", required = false) Long nowCategory){
		List<CategoriesDTO> categories = onlineClassService.categoryFind()
				.stream()
				.map(CategoriesDTO::new)
				.collect(Collectors.toList());

		
		List<OnlineClassList> classes = null;
		if(category == null && nowCategory == null) {
			if(searchKeyword == null) {
				classes = onlineClassService.findAll(Status.Y)
						.stream()
						.map(OnlineClassList::new)
						.collect(Collectors.toList());
				
			}else {
				classes = onlineClassService.findSearchList(searchKeyword, Status.Y)
						.stream()
						.map(OnlineClassList::new)
						.collect(Collectors.toList());
			}
		}else {
			model.addAttribute("nowCategory", category);
			if(searchKeyword == null) {
				classes = onlineClassService.findCategoryList(category, Status.Y)
						.stream()
						.map(OnlineClassList::new)
						.collect(Collectors.toList());
			}else {
				classes = onlineClassService.findCategorySearchList(searchKeyword, nowCategory, Status.Y)
						.stream()
						.map(OnlineClassList::new)
						.collect(Collectors.toList());
			}
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("categories", categories);
		
		return "onlineClass/onlineClassList";
	}
	
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
