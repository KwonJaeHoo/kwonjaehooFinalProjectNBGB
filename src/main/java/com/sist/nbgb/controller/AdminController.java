package com.sist.nbgb.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.service.AdminService;
import com.sist.nbgb.service.InstructorsService;
import com.sist.nbgb.service.ReferenceService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final ReferenceService referenceService;
	private final AdminService adminService;
	
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
	@GetMapping("/userList")
	public String userList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@PageableDefault(size = 5, sort = "userRegdate", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<User> page;
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			page = adminService.findUserByKeyword(searchKeyword, pageable);
		} else {
			page = adminService.findByUserRegdate(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("userList", page.getContent());                                
		
		return "admin/userList";
	}
	
	//일반회원 권한 정지
	@PostMapping("/admin/roleUser")
	public ResponseEntity<User> roleUser(@RequestParam("userId") String userId,
										 @RequestParam("Authority") String Authority) {
	}
	
	//강사회원 리스트 불러오기
	@GetMapping("/instructorList")
	public String instructorList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@PageableDefault(size = 5, sort = "instructorRegdate", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<Instructors> page;
		if(searchKeyword != null && !searchKeyword.isEmpty()) {
			page = adminService.findInsturctorByKeyword(searchKeyword, pageable);
		} else {
			page = adminService.findAllByOrderByInstructorRegdate(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("instructorList", page.getContent());
		
		return "admin/instructorList";
	}
	
	//온라인 강의 리스트 불러오기
	@GetMapping("/onlineClassList")
	public String onlineClassList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@PageableDefault(size = 5, sort = "onlineClassRegdate", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<OnlineClass> page;
		if(searchKeyword != null && !searchKeyword.isEmpty()) {
			page = adminService.findOnlineClassByKeyword(searchKeyword, pageable);
		} else {
			page = adminService.findByOnlineClassRegdate(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("onlineClassList", page.getContent());
		
		return "admin/onlineClassList";
	}
	
	//오프라인 강의 리스트 불러오기
	@GetMapping("/offlineClassList")
	public String offlineClassList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@PageableDefault(size = 5, sort = "offlineClassRegdate", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<OfflineClass> page;
		if(searchKeyword != null && !searchKeyword.isEmpty()) {
			page = adminService.findOfflineClassByKeyword(searchKeyword, pageable);
		} else {
			page = adminService.findByOfflineClassRegdate(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("offlineClassList", page.getContent());
		
		return "admin/offlineClassList";
	}
	
}
































