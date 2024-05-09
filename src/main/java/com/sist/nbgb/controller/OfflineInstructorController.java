package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.OfflineResponse;
import com.sist.nbgb.service.OfflineInstructorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OfflineInstructorController 
{
	private final OfflineInstructorService offlineInstructorService;
	
	@GetMapping("/instructor/mypage/{id}/offlinelecture")
	public String offlinelecture(Model model, @PathVariable String id, @RequestParam(value="page", defaultValue="0") int page)
	{
		List<OfflineResponse> list = offlineInstructorService.findByInstructorIdDesc(id).stream()
				.map(OfflineResponse::new)
				.collect(Collectors.toList());
		
		Page<OfflineClass> paging = this.offlineInstructorService.findByInstructorId(page, id);
		Page<OfflineResponse> toMap = paging.map(m -> new OfflineResponse(m));
		List<OfflineResponse> listPaging = toMap.getContent();
		
		Status N = Status.N;
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("listPaging", listPaging);
		model.addAttribute("N", N);
		
		return "/mypage/instructor/offlinelecture";
	}
	
	@GetMapping("/instructor/mypage/{id}/offlinelecturerequest")
	public String offlinelecturerequest(Model model, @PathVariable String id)
	{		
		return "/mypage/instructor/offlinelecturerequest";
	}
}
