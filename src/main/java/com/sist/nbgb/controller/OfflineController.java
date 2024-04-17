package com.sist.nbgb.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.OfflineResponse;
import com.sist.nbgb.service.OfflineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OfflineController
{
	private final OfflineService offlineService;
	
	@GetMapping("/offlineClass")
	public String offline(Model model, String searchType, String searchValue)
	{
		List<OfflineResponse> list = null;
		
		if(searchValue == null)
		{
			list = offlineService.findByUpload(Status.Y).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
		}
		else if(searchType.equals("1"))
		{
			list = offlineService.findByofflineClassCategoryContaining(searchValue).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
		}
		else if(searchType.equals("2"))
		{
			if(searchValue.equals("In"))
			{	
				list = offlineService.findByOfflineClassPlaceOrOfflineClassPlaceContaining("인천"	, "경기").stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "인천, 경기";
			}
			else if(searchValue.equals("Bu"))
			{
				list = offlineService.findByOfflineClassPlaceOrOfflineClassPlaceContaining("부산", "경상").stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "부산, 경상";
			}
			else if(searchValue.equals("Da"))
			{
				list = offlineService.findByOfflineClassPlaceOrOfflineClassPlaceContaining("대전", "충청").stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "대전, 충청";
			}
			else if(searchType.equals("Ga"))
			{
				list = offlineService.findByOfflineClassPlaceOrOfflineClassPlaceContaining("광주", " 전라").stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "광주, 전라";
			}
			else
			{
				list = offlineService.findByOfflineClassPlaceContaining(searchValue).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
			}
		}
		else if(searchType.equals("3"))
		{
			
		}
		
		Collections.reverse(list);
		
		model.addAttribute("list", list);
		model.addAttribute("searchValue", searchValue);
		
		return "/offline/offlineList";
	}
}
