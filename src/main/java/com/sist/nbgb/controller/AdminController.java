package com.sist.nbgb.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.service.ReferenceService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final ReferenceService referenceService;
	
	@GetMapping("/adminMain")
	public String adminMain(Model model)
	{
		return "/admin/adminMain";
	}
	
	//문의 게시판 페이지 불러오기(최신순 조회)
	@GetMapping("/referenceList")
	public String referenceList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@PageableDefault(size = 5, sort = "refRegdate", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<Reference> page;
	    if (searchKeyword != null && !searchKeyword.isEmpty()) {
	        page = referenceService.findByKeyword(searchKeyword, pageable);
	    } else {
	        page = referenceService.findByrefRegdate(pageable);
	    }
	    
	    model.addAttribute("page", page);
		model.addAttribute("referenceList", page.getContent());
		
		return "admin/referenceList";
	}
	
	//문의 게시글 상세페이지
	@GetMapping("/referenceView/{refId}")
	public String referenceView(Model model, @PathVariable Long refId) {
		
		List<Reference> refList = referenceService.findByView(refId);
		
		model.addAttribute("refList", refList);
		
		
		return "admin/referenceView";
	}
	
	
	//일반회원 리스트 불러오기
	@GetMapping("/admin/userList")
	public String userList(Model  model)
	{
		return "admin/userList";
	}
}
































