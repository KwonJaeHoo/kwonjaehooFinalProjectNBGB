package com.sist.nbgb.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nbgb.dto.CategoriesDTO;
import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OnlineClassList;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.dto.OnlineReviewCommentDTO;
import com.sist.nbgb.dto.OnlineReviewDTO;
import com.sist.nbgb.entity.ClassLike;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	//온라인 강의 상세 조회
	@GetMapping("/online/{onlineClassId}")
	public String onlineView(@PathVariable Long onlineClassId, Model model, @RequestParam(value="page", defaultValue="0") int page) {
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		
		//페이징 안한 리뷰목록
		List<OnlineReviewDTO> reviewList = onlineClassService.findOnReview(onlineClassId, "ON", Status.Y)
				.stream()
				.map(OnlineReviewDTO::new)
				.collect(Collectors.toList());
		
		//페이징 리뷰목록
		Page<Review> paging = this.onlineClassService.getList(page, onlineClassId, "ON", Status.Y);
		Page<OnlineReviewDTO> toMap = paging.map(m -> new OnlineReviewDTO(m));
		List<OnlineReviewDTO> rev = toMap.getContent();
		
		model.addAttribute("paging", paging);
		model.addAttribute("rev", rev);
		
		
		//로그인 한 상태일때만 조회하도록 설정 필요
		//결제 시간
		String str = String.valueOf(onlineClassId);
		//쿠키로 현재 로그인 한 id 가져와야함
		LocalDateTime approvedAt = onlineClassService.findApproveAt(str, "sist1");
		//강의 수강 기간
		long period = onlineClass.getOnlineClassPeriod();
		//강의 들을 수 있는 날짜
		LocalDateTime endDate = null;
		//결제 여부(기본 N, 결제 완료되어 수강 할 수 있는 상태면 Y)
		String payStatus = "N";
		
		if(approvedAt != null) {
			endDate = approvedAt.plusDays(period);
			
			if(LocalDateTime.now().isBefore(endDate) || LocalDateTime.now().isEqual(endDate)) {
				payStatus = "Y";
			}
		}

		//좋아요 수
		long likeCnt = 0;
		likeCnt = onlineClassService.findLikeCnt(onlineClassId);
		
		//내가 좋아요 상태
		String likeStatus = "N";
		
		//쿠키아이디가져와야됨
//		if(onlineClassService.findLikeMe(onlineClassId, "on", "sist1") > 0) {
//			likeStatus = "Y";
//		}
		
		//조회수 증가
		onlineClassService.updateViews(onlineClassId);
		
		//후기 개수
		long reviewCnt = 0;
		reviewCnt = onlineClassService.findReviewCnt(onlineClassId, "ON", Status.Y);
		
		//후기 별점 평균
		int starAvg = 0;
		
		if(reviewCnt > 0) {
			starAvg = onlineClassService.starAvg(onlineClassId);
		}
		
		//후기 댓글 목록
		List<OnlineReviewCommentDTO> commentList = onlineClassService.findOnlineComment(onlineClassId)
				.stream()
				.map(OnlineReviewCommentDTO::new)
				.collect(Collectors.toList());
	
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("payStatus", payStatus);
		model.addAttribute("likeStatus", likeStatus);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("starAvg", starAvg);
		model.addAttribute("likeDto", new ClassLikeDTO());
		model.addAttribute("commentList", commentList);
		
		return "onlineClass/onlineClassView";
	}

	@PostMapping("/api/onlineLike")
	public String onlineLike(@ModelAttribute("likeDto") ClassLikeDTO classLikeDto, Model model) {
		log.info(classLikeDto.getClassId()+"classID");
		log.info(classLikeDto.getClassIden()+"iden");
		log.info(classLikeDto.getUserId()+"userID");
		
		
		try {
			ClassLike classLike = ClassLike.createClassLike(classLikeDto);
			onlineClassService.saveLike(classLike);
		} catch(IllegalStateException e) {
			model.addAttribute("errorMsg", e.getMessage());
		}
		
		return "redirect:/";
	}
}
