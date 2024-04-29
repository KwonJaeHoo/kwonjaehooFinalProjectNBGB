package com.sist.nbgb.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OfflineClassIdDto;
import com.sist.nbgb.dto.OfflineLikeDto;
import com.sist.nbgb.dto.OfflinePostDto;
import com.sist.nbgb.dto.OfflineUpload;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewComment;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.OfflineResponse;
import com.sist.nbgb.response.OfflineReviewCommentResponse;
import com.sist.nbgb.response.OfflineReviewResponse;
import com.sist.nbgb.response.UserResponse;
import com.sist.nbgb.service.OfflineReviewService;
import com.sist.nbgb.service.OfflineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OfflineController
{
	private final OfflineService offlineService;
	private final OfflineReviewService offlineReviewService;
	
	@Autowired
	private OfflineUpload photoUtil;
	
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
	public String offlineView(Model model, @PathVariable Long offlineClassId, @RequestParam(value="page", defaultValue="0") int page)
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
					Page<Review> paging = this.offlineReviewService.reviewListPage(page, offlineClassId, "OFF", Status.Y);
					Page<OfflineReviewResponse> toMap = paging.map(m -> new OfflineReviewResponse(m));
					List<OfflineReviewResponse> listPaging = toMap.getContent();
					
					rating = offlineReviewService.offCountRating(offlineClassId);
					count = offlineReviewService.offCount(offlineClassId);
					
					commentlist = offlineReviewService.findReviewComment(offlineClassId).stream()
							.map(OfflineReviewCommentResponse::new)
							.collect(Collectors.toList());
					
//					System.out.println(commentlist);
					
					avgRating = (rating / 5 ) * 100;
					
					model.addAttribute("paging", paging);
					model.addAttribute("listPaging", listPaging);
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
	
	//찜
	//찜 중복확인
	@ResponseBody
	@PostMapping("/offlineClass/likeChk")
	public ResponseEntity<Integer> LikeChk(Long offlineClassId)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		int cnt = 1;
		
		if(userId == null)
		{
			cnt = -1;
		}
		else
		{
			cnt = offlineService.duplicationLike(offlineClassId, userId);
		}
		
		return ResponseEntity.ok(cnt);
	}
	
	@ResponseBody
	@PostMapping("/offlineClass/like")
	public ResponseEntity<OfflineLikeDto> like(@RequestPart(value="likeDto") ClassLikeDTO classLikeDto)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		OfflineClassIdDto offClassId = new OfflineClassIdDto(classLikeDto, userId);
		
		System.out.println(classLikeDto.getClassId());
		
		offClassId.setClassId(classLikeDto.getClassId());
		offClassId.setClassIden("OFF");
		offClassId.setUserId(userId);
		
		OfflineLikeDto likeDto = new OfflineLikeDto();
		
		likeDto.createClassLike(offClassId);
		
		return ResponseEntity.ok(offlineService.offlineLike(likeDto));
	}
	
	//오프라인 게시물 등록
	//페이지 불러오기
	@GetMapping("/offlineClass/write")
	public String offlineClassWrite()
	{
		return "/offline/offlineClassWrite";
	}
	
	//에디터 파일 저장
	@ResponseBody
	@PostMapping("/images/offlineUpload")
	public ModelAndView offlineUpload(MultipartHttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView("jsonView");
		
		String uploadPath = photoUtil.ckUpload(request);
		
		mav.addObject("uploaded", true);
		mav.addObject("url", "/images/offlineUpload" + uploadPath);
		
		return mav;
	}
	
	//게시물 등록
	@PostMapping("/offlineClass/write/post")
	@ResponseBody
	public ResponseEntity<OfflinePostDto> offlinePost(@RequestPart(value="offlinePostDto") @Valid OfflinePostDto offlinePostDto, @RequestPart(value="offlineFile") MultipartFile offlineFile)
	{
		OfflinePostDto offDto = offlineService.offlinePost(offlinePostDto);
		
		String path = "C:/project/sts4/SFPN/src/main/resources/static/images/offlineThumbnail";
		
		String fileName = offDto.getOfflineClassId() + ".jpg";
		
		String filepath = path + "/" + fileName;
		
		File file = new File(filepath);
		
		try 
	    {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(offlineFile.getBytes());
            bufferedOutputStream.close();
	        
	    }
	    catch (Exception e) 
        {
            throw new RuntimeException("오류가 발생했습니다.");
        } 
	       
       return ResponseEntity.ok(offDto);   

	}
	
	
	//예약하기
	@GetMapping("/offlineClass/reserve/{offlineClassId}")
	public String offlineClassReserve(Model model, @PathVariable Long offlineClassId)
	{
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		OfflineClass offlineClass = null;
		Optional<UserResponse> user = null;
		
		
		if(offlineClassId <= 0)
		{
			System.out.println("클래스 오류");
			return "/offline/error";
		}
		else
		{
			offlineClass = offlineService.findByView(offlineClassId);
			
			if(offlineClass == null)
			{
				System.out.println("클래스 없음");
				return "/offline/error";
			}
			else
			{
				if(userid == "" || userid == null || userid == " ")
				{
					return "/login/login";
				}
				else
				{
					user = offlineService.findByUserId(userid)
							.map(UserResponse::new);
					
					if(user == null)
					{
						return "/signup/signup";
					}
					else
					{
						System.out.println(user.get().getUserStatus());
						
						if(user.get().getUserStatus().equals(Status.Y))
						{
							model.addAttribute("user", user.get());
							model.addAttribute("offlineClass", new OfflineResponse(offlineClass));
							
							return "/offline/offlineReserve";
						}
						else
						{
							System.out.println("상태 이상");
							return "/offline/error";
						}
					}
				}
			}
		}
	}
}
