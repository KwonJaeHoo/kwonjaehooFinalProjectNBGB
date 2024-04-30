package com.sist.nbgb.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.ReferenceDTO;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.service.ReferenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReferenceController {
	
	private final ReferenceService referenceService;
	
	//자주 묻는 질문 페이지 불러오기
	@GetMapping("/reference/FAQ")
	public String FAQ(Model model)
	{
		return "reference/FAQ";
	}
	
	//문의 게시판 페이지 불러오기(최신순 조회)
	@GetMapping("/reference/referenceList")
	public String referenceList(Model model)
	{
		List<Reference> list = referenceService.findByrefRegdate();
		
		model.addAttribute("referenceList", list);
		
		return "reference/referenceList";
	}
	
	@GetMapping("/reference/referenceWrite")
	public String referenceWrite(Model model)
	{
		return "reference/referenceWrite";
	}
	
	//문의 등록
	@PostMapping("/reference/post")
	@ResponseBody
	public ResponseEntity<ReferenceDTO> referencePost(@RequestPart(value="referenceDTO") @Valid ReferenceDTO referenceDTO)
	{
		ReferenceDTO refDTO = referenceService.referenceDTO(referenceDTO);
		
		return ResponseEntity.ok(refDTO);
	}

}
