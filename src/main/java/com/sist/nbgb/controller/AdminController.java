package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OfflineClassDenyDTO;
import com.sist.nbgb.dto.OfflineClassStatusChange;
import com.sist.nbgb.dto.OnlineClassDenyDTO;
import com.sist.nbgb.dto.OnlineClassStatusChange;

import com.sist.nbgb.dto.ReferenceAnswerDto;
import com.sist.nbgb.dto.ReferenceDto2;
import com.sist.nbgb.dto.ReviewReportListDTO;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.ReviewReport;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.AdminService;
import com.sist.nbgb.service.ReferenceService;
import com.sist.nbgb.service.ReviewService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final ReferenceService referenceService;
	private final AdminService adminService;
	private final ReviewService reviewService;
	private final UserService userService;
	
	@GetMapping("/adminMain")
	public String adminMain(Model model,
			@Qualifier("user") @PageableDefault(size = 5, sort = "userRegdate", direction = Sort.Direction.DESC) Pageable userPageable,
			@Qualifier("instructor") @PageableDefault(size = 5, sort = "instructorRegdate", direction = Sort.Direction.DESC) Pageable instructorPageable,
			@Qualifier("onlineClass") @PageableDefault(size = 5, sort = "onlineClassRegdate", direction = Sort.Direction.DESC) Pageable onlineClassPageable,
			@Qualifier("offlineClass") @PageableDefault(size = 5, sort = "offlineClassRegdate", direction = Sort.Direction.DESC) Pageable offlineClassPageable,
			@Qualifier("reference") @RequestParam(value = "page", defaultValue="0") int page) {
        
		Page<User> userPage = adminService.findByUserRegdate(userPageable);
        Page<Instructors> instructorPage = adminService.findAllByOrderByInstructorRegdate(instructorPageable);
        Page<OnlineClass> onlineClassPage = adminService.findAllByApproveStatusAndOrderByRegdateDesc(onlineClassPageable);
        Page<OfflineClass> offlineClassPage = adminService.findOfflineByApproveStatusAndOrderByRegdateDesc(offlineClassPageable);
        List<ReferenceDto2> listPaging = referenceService.findByrefRegdate()
        								 .stream().map(ReferenceDto2::new).collect(Collectors.toList());
        
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "refId"));
		int start = (int) pageRequest.getOffset();
		int end = Math.min(start + pageRequest.getPageSize(), listPaging.size());
		Page<ReferenceDto2> paging = new PageImpl<>(listPaging.subList(start, end), pageRequest, listPaging.size());
        
        model.addAttribute("userList", userPage.getContent());
        model.addAttribute("instructorList", instructorPage.getContent());
        model.addAttribute("onlineClassList", onlineClassPage.getContent());
        model.addAttribute("offlineClassList", offlineClassPage.getContent());
        model.addAttribute("paging", paging);
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
	
	//문의글 답변 달기
	@GetMapping("/refAnswerPop")
	public String refAnswerPop(Model model) {
		    
	    return "/admin/refAnswerPop";
	}
	
	@PostMapping("/refAnswer")
	@ResponseBody
    public String refAnswer(@RequestBody ReferenceAnswerDto ReferenceAnswerDto ) {
        try
        {
        	adminService.refAnswer(ReferenceAnswerDto.getRefId(), ReferenceAnswerDto.getRefAnswerContent());
        	return "SUCCESS";
        } catch(Exception e) {
        	e.printStackTrace();
        	return "ERROR";
        }
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
		model.addAttribute("orderBy", "user");                                
		
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
		model.addAttribute("orderBy", "instructor");
		
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
		model.addAttribute("orderBy", "online");
		
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
	
	//온라인 강의 자료 다운로드 페이지 가기
	@GetMapping("/onlineClassFile/{onlineClassId}")
	public String onlineClassFile(Model model, @PathVariable OnlineClass onlineClassId)
	{
		List<OnlineClassFile> onlineClassFile = adminService.findOnlineClassFilesByClassId(onlineClassId);
        model.addAttribute("onlineClassFile", onlineClassFile);
        return "admin/onlineClassFile";
	}
	
	//온라인 강의 자료 다운로드
	@GetMapping("/downloadFile/{onlineClassId}/{onlineFileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable final long onlineClassId, @PathVariable final long onlineFileId,
												 HttpServletRequest request) 
	{
	    OnlineClassFile file = adminService.findOnlineClassFileById(onlineClassId, onlineFileId);
	    
	    if(file == null) 
	    {
	        return ResponseEntity.notFound().build();
	    }
	    
	    try 
	    {
	    	String baseDirectory = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\video";
	        Path filePath = Paths.get(baseDirectory, file.getOnlineFileName()).normalize();
	        
	        Resource resource = new UrlResource(filePath.toUri());
	        if (resource.exists() && resource.isReadable())
	        {
	            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	            
	            if (contentType == null)
	            {
	                contentType = "application/octet-stream";
	            }
	            
	            String originalFileName = file.getOnlineFileName();
	            String encodedOriginalFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

	            String contentDisposition = "attachment; filename*=UTF-8''" + encodedOriginalFileName;

	            return ResponseEntity
	                    .ok()
	                    .contentType(MediaType.parseMediaType(contentType))
	                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    catch (Exception e)
	    {
	        return ResponseEntity.badRequest().build();
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
		model.addAttribute("orderBy", "offline");
		
		return "admin/offlineClassList";
	}
	
	//오프라인 미승인 강의 승인하기
	@PostMapping("/offlineApprove")
	@ResponseBody
	public String changeOfflineToApprove(@RequestBody OfflineClassStatusChange offlineClassStatusChange) 
	{
		try 
		{
			adminService.changeOfflineToApprove(offlineClassStatusChange.getOfflineClassId(), Status.Y);
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
	public String reviewReportList(Model model, @PageableDefault(size = 5) Pageable pageable, 
			@RequestParam(value = "orderBy", required = false, defaultValue="all") String orderBy) {
		if(orderBy.equals("all")) {
			Page<ReviewReport> reportList = reviewService.reviewReportList(pageable.getPageNumber());
			model.addAttribute("reportList", reportList);
		}else {
			Page<ReviewReport> reportList = reviewService.findNotRecivedList(pageable.getPageNumber());
			model.addAttribute("reportList", reportList);
		}
		model.addAttribute("orderBy", orderBy);
		return "admin/reviewReportList";
	}
	
	//후기 신고 처리
	@GetMapping("/reviewReportPop/{reviewId}/{userId}")
	public String reviewReportPop(Model model, @PathVariable(value="reviewId")Long reviewId, @PathVariable(value="userId")String userId) {
		ReviewReportListDTO report = new ReviewReportListDTO(reviewService.reviewReportView(reviewId, userId));
		
		model.addAttribute("report", report);
		return "admin/reviewReportPop";
	}
	
	@ResponseBody
	@PutMapping("/reviewReportPop")
	public void reviewReportProcess(@RequestParam(value="reportValue") String reportValue, @RequestParam(value="reportAll") String reportAll, 
				@RequestParam(value="reviewId") Long reviewId, @RequestParam(value="reportUserId")String reportUserId) {
		if(reportValue.equals("block") && reviewService.reviewStatusCheck(reviewId).equals(Status.Y)) {
			reviewService.updateReviewStatus(reviewId, Status.C);
		}
		
		if(reportAll.equals("true")) {
			reviewService.updateAllReportStatus(reviewService.findByReviewId(reviewId), Status.C);
		}else {
			reviewService.updateReportStatus(reviewService.findByReviewId(reviewId), userService.findUserById(reportUserId), Status.C);
		}
	}
}