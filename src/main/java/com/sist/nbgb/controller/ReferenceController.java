package com.sist.nbgb.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nbgb.dto.ReferenceDTO;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.service.ReferenceService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReferenceController {
	
	private final ReferenceService referenceService;
	private final UserService userService;
	
	//자주 묻는 질문 페이지 불러오기
	@GetMapping("/reference/FAQ")
	public String FAQ(Model model)
	{
		return "reference/FAQ";
	}
	
	@GetMapping("/reference/referenceWrite")
	public String referenceWrite(Model model)
	{
		return "reference/referenceWrite";
	}
	
	//문의 등록
	@PostMapping("/reference/referenceWrite/post")
	public ModelAndView writeReference(@RequestParam("refTitle") String refTitle,
	                                   @RequestParam("refContent") String refContent,
	                                   Principal principal) {
	    String userId = principal.getName();
	    
	    if (userId == null || userId.trim().isEmpty()) {
	        throw new IllegalArgumentException("userId는 null일 수 없습니다.");
	    }
	    
	    User user = userService.findUserById(userId); 
	    
	    ReferenceDTO referenceDTO = ReferenceDTO.builder()
	            .refTitle(refTitle)
	            .refContent(refContent)
	            .userId(user)
	            .build();

	    referenceService.saveReference(referenceDTO);
	    return new ModelAndView("redirect:/reference/referenceList");
	}

}
