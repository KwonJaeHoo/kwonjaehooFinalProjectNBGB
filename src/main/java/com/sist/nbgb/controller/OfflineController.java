package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.OfflineResponse;
import com.sist.nbgb.service.OfflineReviewService;
import com.sist.nbgb.service.OfflineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OfflineController
{
	private final OfflineService offlineService;
	private final OfflineReviewService offlineReviewService;
	
	@GetMapping("/offlineClass")
	public String offline(Model model, String searchType, String searchValue)
	{
		List<OfflineResponse> list = null;
		List<OfflineResponse> Blist = null;
		
		if(searchValue == null)
		{
			list = offlineService.findByUpload(Status.Y).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
			
			Blist = offlineService.findByUpload(Status.B).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
		}
		else if(searchType.equals("1"))
		{
			list = offlineService.findByCategory(searchValue, Status.Y).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
			
			Blist = offlineService.findByCategory(searchValue, Status.B).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
		}
		else if(searchType.equals("2"))
		{
			if(searchValue.equals("In"))
			{	
				list = offlineService.findByTwoPlace("인천"	, "경기", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("인천"	, "경기", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "인천, 경기";
			}
			else if(searchValue.equals("Bu"))
			{
				list = offlineService.findByTwoPlace("부산", "경상", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("부산", "경상", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "부산, 경상";
			}
			else if(searchValue.equals("Da"))
			{
				list = offlineService.findByTwoPlace("대전", "충청", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("대전", "충청", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "대전, 충청";
			}
			else if(searchValue.equals("Ga"))
			{
				list = offlineService.findByTwoPlace("광주", " 전라", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("광주", " 전라", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				searchValue = "광주, 전라";
			}
			else
			{
				list = offlineService.findByPalce(searchValue, Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByPalce(searchValue, Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
			}
		}
		else if(searchType.equals("3"))
		{
			list = offlineService.findBySearch(searchValue, Status.Y).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
			
			Blist = offlineService.findBySearch(searchValue, Status.B).stream()
					.map(OfflineResponse::new)
					.collect(Collectors.toList());
		}
		
		model.addAttribute("list", list);
		model.addAttribute("Blist", Blist);
		model.addAttribute("searchValue", searchValue);
		
		return "/offline/offlineList";
	}
	
	@GetMapping("/offlineClass/{offlineClassId}")
	public String offlineView(Model model, @PathVariable Long offlineClassId)
	{
		OfflineClass offlineClass = null;
		
		if(offlineClassId > 0)
		{
			offlineClass = offlineService.findByView(offlineClassId);
			
			if(offlineClass !=  null)
			{
				float rating = 0;
				int count = 0;
				float avgRating = 0;
				
				List<Review> review = offlineReviewService.findReview(offlineClassId);
				
				offlineService.updateViews(offlineClassId);
				
				if(review != null)
				{
					rating = offlineReviewService.offCountRating(offlineClassId);
					count = offlineReviewService.offCount(offlineClassId);
					
					avgRating = (rating / 5 ) * 100;
					
					System.out.println("평균 : " + rating);
					System.out.println("평균 % : " + avgRating);
				}
				
				model.addAttribute("review", review);
				model.addAttribute("count", count);
				model.addAttribute("avgRating", avgRating);
				model.addAttribute("offlineClass", new OfflineResponse(offlineClass));
				model.addAttribute("rating", rating);
				
				return "/offline/offlineClassView";
			}
			else
			{
				return "/offline/error";
			}
		}
		else
		{
			return "/offline/error";
		}
	}
}
