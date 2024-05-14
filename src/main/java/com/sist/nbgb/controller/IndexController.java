package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.dto.OfflineClassView;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.dto.ReviewDTO;
import com.sist.nbgb.dto.ReviewFileDto;
import com.sist.nbgb.dto.UserDto;
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
		//온라인(내방) 클래스
		List<OnlineClassView> onlineClassView = indexService.indexOnlineClassView()
				.stream()
				.map(OnlineClassView::new)
				.collect(Collectors.toList());
		
		model.addAttribute("onlineClassView", onlineClassView);
		
		//온라인(내방) 클래스 수 체크
		int onlineClassCount = onlineClassView.size();
		
		model.addAttribute("onlineClassCount", onlineClassCount);
		
		//오프라인(공방) 클래스
		List<OfflineClassView> offlineClassView = indexService.indexOfflineClassView()
				.stream()
				.map(OfflineClassView::new)
				.collect(Collectors.toList());
		
		model.addAttribute("offlineClassView", offlineClassView);
		
		//오프라인(공방) 클래스 수 체크
		int offlineClassCount = offlineClassView.size();
		
		model.addAttribute("offlineClassCount", offlineClassCount);
		
		//후기
		List<ReviewDTO> reviewDTO = indexService.reviewDTO()
				.stream()
				.map(ReviewDTO::new)
				.collect(Collectors.toList());
		
		for(ReviewDTO reviews : reviewDTO) {
			if(indexService.getImg(reviews.getUserId().getUserId()) == "Y") {
				reviews.setImg("Y");
			}
			
			if(reviews.getClassIden().equals("ON")) {
				reviews.setClassName(indexService.findOnTitle(reviews.getClassId()));
			} else if (reviews.getClassIden().equals("OFF")) {
				reviews.setClassName(indexService.findOffTitle(reviews.getClassId()));
			}
		}
		
		model.addAttribute("reviewDTO", reviewDTO);
		
		//후기 개수 체크
		int reviewCount = reviewDTO.size();
		
		model.addAttribute("reviewCount", reviewCount);
		
		//일반 회원
		List<UserDto> userDto = indexService.userDto()
				.stream()
				.map(UserDto::from)
				.collect(Collectors.toList());
		
		model.addAttribute("userDto", userDto);
		
		//일반 회원 수 체크
		int userCount = userDto.size();
		
		model.addAttribute("userCount", userCount);
		
		//후기 사진
		List<ReviewFileDto> reviewFileDto = indexService.reviewFileDto()
				.stream()
				.map(ReviewFileDto::new)
				.collect(Collectors.toList());
		
		System.out.println(reviewFileDto);
		model.addAttribute("reviewFileDto", reviewFileDto);
		
		return "index";
	}
}