package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OfflineClassDenyDTO;
import com.sist.nbgb.dto.OfflineClassStatusChange;
import com.sist.nbgb.dto.OnlineClassDenyDTO;
import com.sist.nbgb.dto.OnlineClassStatusChange;
import com.sist.nbgb.dto.ReferenceDto2;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.AdminService;
import com.sist.nbgb.service.ReferenceService;
import com.sist.nbgb.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final ReferenceService referenceService;
	private final AdminService adminService;
	private final ReviewService reviewService;
	
	@GetMapping("/adminMain")
	public String adminMain(Model model)
	{
		return "/admin/adminMain";
	}
	
	//문의 게시판 페이지 불러오기(최신순 조회)
	@GetMapping("/referenceList")
	public String referenceList(Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "page", defaultValue="0") int page)
	{			
		List<ReferenceDto2> listPaging;
	    if(searchKeyword != null && !searchKeyword.isEmpty())
	    {
	        listPaging = referenceService.findByKeyword(searchKeyword)
	        		.stream().map(ReferenceDto2::new).collect(Collectors.toList());
	    }
	    else
	    {
	        listPaging = referenceService.findByrefRegdate()
	        		.stream().map(ReferenceDto2::new).collect(Collectors.toList());
	    }
	    
	    if(!listPaging.isEmpty())
	    {
	    	adminService.referenceAnswerFind(listPaging);
	    }
    
	    PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "refId"));
		int start = (int) pageRequest.getOffset();
		int end = Math.min(start + pageRequest.getPageSize(), listPaging.size());
		Page<ReferenceDto2> paging = new PageImpl<>(listPaging.subList(start, end), pageRequest, listPaging.size());

	    
	    model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("paging", paging);
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
	@PostMapping("/roleStop")
	@ResponseBody
	public String changeUserRoleToStop(@RequestBody UserIdCheckDto userIdCheckDto) {
		try {
			adminService.changeUserRole(userIdCheckDto.getUserId(), Role.ROLE_STOP);
			return "SUCCESS";
		} catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	//일반회원 권한 복구
	@PostMapping("/roleUser")
	@ResponseBody
	public String changeUserRoleToUser(@RequestBody UserIdCheckDto userIdCheckDto) {
		try {
			adminService.changeUserRole(userIdCheckDto.getUserId(), Role.ROLE_USER);
			return "SUCCESS";
		} catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
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
			page = adminService.findAllByApproveStatusAndOrderByRegdateDesc(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("onlineClassList", page.getContent());
		
		return "admin/onlineClassList";
	}
	
	//온라인 미승인 강의 승인하기
	@PostMapping("/onlineApprove")
	@ResponseBody
	public String changeOnlineToApprove(@RequestBody OnlineClassStatusChange onlineClassStatusChange) 
	{
		try 
		{
			adminService.changeOnlineToApprove(onlineClassStatusChange.getOnlineClassId(), Status.Y);
			return "SUCCESS";
		} catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	//온라인 미승인 강의 반려하기
	@GetMapping("/onlineClassDenyPop")
	public String onlineClassDeny(Model model) {
	    
	    return "/admin/onlineClassDenyPop";
	}
	
	@PostMapping("/onlineClassDeny")
	@ResponseBody
    public String denyOnlineClass(@RequestBody OnlineClassDenyDTO onlineClassDenyDto) {
        try
        {
        	adminService.denyOnlineClass(onlineClassDenyDto.getOnlineClassId(), onlineClassDenyDto.getRejection());
        	return "SUCCESS";
        } catch(Exception e) {
        	e.printStackTrace();
        	return "ERROR";
        }
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
			page = adminService.findOfflineByApproveStatusAndOrderByRegdateDesc(pageable);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("offlineClassList", page.getContent());
		
		return "admin/offlineClassList";
	}
	
	//오프라인 미승인 강의 승인하기
	@PostMapping("/offlineApprove")
	@ResponseBody
	public String changeOfflineToApprove(@RequestBody OfflineClassStatusChange offlineClassStatusChange) 
	{
		try 
		{
			adminService.changeOnlineToApprove(offlineClassStatusChange.getOfflineClassId(), Status.Y);
			return "SUCCESS";
		} catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	//오프라인 미승인 강의 반려하기
	@GetMapping("/offlineClassDenyPop")
	public String offlineClassDeny(Model model) {
	    
	    return "/admin/offlineClassDenyPop";
	}
	
	@PostMapping("/offlineClassDeny")
	@ResponseBody
    public String denyOfflineClass(@RequestBody OfflineClassDenyDTO offlineClassDenyDto) {
        try
        {
        	adminService.denyOfflineClass(offlineClassDenyDto.getOfflineClassId(), offlineClassDenyDto.getRejection());
        	return "SUCCESS";
        } catch(Exception e) {
        	e.printStackTrace();
        	return "ERROR";
        }
    }
	
	
	//후기 신고 목록
	@GetMapping("/reviewReportList")
	public String reviewReportList(Model model, @PageableDefault(size = 5) Pageable pageable
			/*@RequestParam(value="page", defaultValue="0")int page*/) {

		Page<ReviewReport> reportList = reviewService.reviewReportList(pageable.getPageNumber());
		model.addAttribute("reportList", reportList);
		model.addAttribute("reportList", reportList);
		model.addAttribute("maxPage", 5);
		
		return "admin/reviewReportList";
	}
	
}