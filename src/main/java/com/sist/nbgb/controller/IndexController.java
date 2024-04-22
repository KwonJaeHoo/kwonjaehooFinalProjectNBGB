package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.dto.OfflineClassView;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.dto.ReviewDTO;
import com.sist.nbgb.service.IndexService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController 
{
	private final IndexService indexService;
	
	@GetMapping("/")
	public String index(Model model)
	{		
		List<OnlineClassView> onlineClassView = indexService.indexOnlineClassView()
				.stream()
				.map(OnlineClassView::new)
				.collect(Collectors.toList());
		
		model.addAttribute("onlineClassView", onlineClassView);
		
		List<OfflineClassView> offlineClassView = indexService.indexOfflineClassView()
				.stream()
				.map(OfflineClassView::new)
				.collect(Collectors.toList());
		
		model.addAttribute("offlineClassView", offlineClassView);
		
		List<ReviewDTO> reviewDTO = indexService.reviewDTO()
				.stream()
				.map(ReviewDTO::new)
				.collect(Collectors.toList());
		
		model.addAttribute("reviewDTO", reviewDTO);
		
		return "index";
	}
}