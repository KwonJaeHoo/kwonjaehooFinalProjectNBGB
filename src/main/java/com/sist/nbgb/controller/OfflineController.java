package com.sist.nbgb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.OfflineResponse;
import com.sist.nbgb.response.OfflineReviewCommentResponse;
import com.sist.nbgb.response.OfflineReviewResponse;
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
	public String offline(Model model,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchPlus", required = false) String searchPlus,
			@RequestParam(value = "PlusType", required = false) String plusType)
	{
		List<OfflineResponse> list = null;
		List<OfflineResponse> Blist = null;
		
		String rename = "";
		String keyword = "";
		
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
				
				rename = "인천, 경기";
			}
			else if(searchValue.equals("Bu"))
			{
				list = offlineService.findByTwoPlace("부산", "경상", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("부산", "경상", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				rename = "부산, 경상";
			}
			else if(searchValue.equals("Da"))
			{
				list = offlineService.findByTwoPlace("대전", "충청", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("대전", "충청", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				rename = "대전, 충청";
			}
			else if(searchValue.equals("Ga"))
			{
				list = offlineService.findByTwoPlace("광주", " 전라", Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findByTwoPlace("광주", " 전라", Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				rename = "광주, 전라";
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
			if(plusType.equals("") || plusType.equals("3"))
			{
				list = offlineService.findBySearch(searchValue, Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findBySearch(searchValue, Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
			}
			else if(plusType.equals("1"))
			{
				list = offlineService.findCateKeyword(searchValue, searchPlus, Status.Y).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				Blist = offlineService.findCateKeyword(searchValue, searchPlus, Status.B).stream()
						.map(OfflineResponse::new)
						.collect(Collectors.toList());
				
				keyword = searchValue;
				searchValue = searchPlus;
				searchType = plusType;
			}
			else if(plusType.equals("2"))
			{
				if(searchPlus.equals("In"))
				{	
					list = offlineService.findTwoPlaceKeyword(searchValue, "인천"	, "경기", Status.Y).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					Blist = offlineService.findTwoPlaceKeyword(searchValue, "인천"	, "경기", Status.B).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					rename = "인천, 경기";
				}
				else if(searchPlus.equals("Bu"))
				{
					list = offlineService.findTwoPlaceKeyword(searchValue, "부산", "경상", Status.Y).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					Blist = offlineService.findTwoPlaceKeyword(searchValue, "부산", "경상", Status.B).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					rename = "부산, 경상";
				}
				else if(searchPlus.equals("Da"))
				{
					list = offlineService.findTwoPlaceKeyword(searchValue, "대전", "충청", Status.Y).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					Blist = offlineService.findTwoPlaceKeyword(searchValue, "대전", "충청", Status.B).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					rename = "대전, 충청";
				}
				else if(searchPlus.equals("Ga"))
				{
					list = offlineService.findTwoPlaceKeyword(searchValue, "광주", " 전라", Status.Y).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					Blist = offlineService.findTwoPlaceKeyword(searchValue, "광주", " 전라", Status.B).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					rename = "광주, 전라";
				}
				else
				{
					list = offlineService.findPlaceKeyword(searchValue, searchPlus, Status.Y).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
					
					Blist = offlineService.findPlaceKeyword(searchValue, searchPlus, Status.B).stream()
							.map(OfflineResponse::new)
							.collect(Collectors.toList());
				}
				
				keyword = searchValue;
				searchValue = searchPlus;
				searchType = plusType;
			}
		}
		
		model.addAttribute("list", list);
		model.addAttribute("Blist", Blist);
		model.addAttribute("rename", rename);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		
		return "/offline/offlineList";
	}
	
	@GetMapping("/offlineClass/{offlineClassId}")
	public String offlineView(Model model, @PathVariable Long offlineClassId)
	{
		OfflineClass offlineClass = null;
		List<OfflineReviewResponse> review = null;
		List<OfflineReviewCommentResponse> commentlist = null;
		
		if(offlineClassId > 0)
		{
			offlineClass = offlineService.findByView(offlineClassId);
			
			if(offlineClass !=  null)
			{
				float rating = 0;
				int count = 0;
				float avgRating = 0;
				
				review = offlineReviewService.findReview(offlineClassId).stream()
						.map(OfflineReviewResponse::new)
						.collect(Collectors.toList());
				
				offlineService.updateViews(offlineClassId);
				
				if(review != null)
				{
					rating = offlineReviewService.offCountRating(offlineClassId);
					count = offlineReviewService.offCount(offlineClassId);
					
					commentlist = offlineReviewService.findReviewComment(offlineClassId).stream()
							.map(OfflineReviewCommentResponse::new)
							.collect(Collectors.toList());
					
//					System.out.println(commentlist);
					
					avgRating = (rating / 5 ) * 100;
					
					System.out.println("평균 : " + rating);
					System.out.println("평균 % : " + avgRating);
				}
				
				model.addAttribute("review", review);
				model.addAttribute("count", count);
				model.addAttribute("avgRating", avgRating);
				model.addAttribute("offlineClass", new OfflineResponse(offlineClass));
				model.addAttribute("rating", rating);
				model.addAttribute("commentlist", commentlist);
				
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
