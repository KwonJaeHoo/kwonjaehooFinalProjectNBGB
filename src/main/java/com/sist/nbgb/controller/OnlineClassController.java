package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.dto.CategoriesDTO;
import com.sist.nbgb.dto.OnlineClassList;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;

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
}
