package com.sist.nbgb.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nbgb.dto.CategoriesDTO;
import com.sist.nbgb.dto.OnlineClassListDTO;
import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OfflineUpload;
import com.sist.nbgb.dto.OnlineClassView;
import com.sist.nbgb.dto.OnlinePostDTO;
import com.sist.nbgb.dto.OnlineReviewCommentDTO;
import com.sist.nbgb.dto.OnlineReviewDTO;
import com.sist.nbgb.dto.OnlineReviewLikeDTO;
import com.sist.nbgb.dto.OnlineUpdateDTO;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.response.UserResponse;
import com.sist.nbgb.service.InstructorsService;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OnlineClassController {
	private final OnlineClassService onlineClassService;
	private final InstructorsService instService;
	
	@Autowired
	private OfflineUpload photoUtil;
	
	//온라인 리스트 조회
	@GetMapping("/onlineClass")
	public String getAllClasses(Model model, 
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "nowCategory", required = false) Long nowCategory,
			@RequestParam(value = "orderBy", required = false, defaultValue="0") int orderBy){
		
		//로그인 아이디 정보 받아서 좋아요 리스트
		
		//정렬기준
		String orderByContent = "정렬기준";
		
		//카테고리명 조회
		List<CategoriesDTO> categories = onlineClassService.categoryFind()
				.stream()
				.map(CategoriesDTO::new)
				.collect(Collectors.toList()); 
		
		List<OnlineClassListDTO> classes = null;
		
		if(orderBy == 0 || orderBy == 1) {
			if(category == null && nowCategory == null) {
				if(searchKeyword == null && (orderBy == 1  || orderBy==0)) {
					classes = onlineClassService.findAll(Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
					orderByContent = "최신순";
				}else{
					classes = onlineClassService.findSearchList(searchKeyword, Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
				}
			}else {
				model.addAttribute("nowCategory", category);
				if(searchKeyword == null) {
					classes = onlineClassService.findCategoryList(category, Status.Y)
							.stream()
							.map(OnlineClassListDTO::new)
							.collect(Collectors.toList());
				}else{
					classes = onlineClassService.findCategorySearchList(searchKeyword, nowCategory, Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
				}
			}
		}else {
			switch(orderBy) {
				case 2:
					classes = onlineClassService.findByOnlineClassApproveOrderByOnlineClassViews(Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
					orderByContent = "조회순";
					break;
				case 3:
					classes = onlineClassService.findByOnlineClassApproveOrderByOnlineClassPriceAsc(Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
					orderByContent = "가격낮은순";
					break;
				case 4:
					classes = onlineClassService.findByOnlineClassApproveOrderByOnlineClassPriceDesc(Status.Y)
							.stream().map(OnlineClassListDTO::new).collect(Collectors.toList());
					orderByContent = "가격높은순";
					break;
			}
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("categories", categories);
		model.addAttribute("orderByContent", orderByContent);
		model.addAttribute("classesSize", classes.size());
	
		log.info("category : " + category + ", nowCategory : " + nowCategory);
		log.info("orderBy : " + orderBy + ", searchKeyword : " + searchKeyword);
		log.info("classesSize" + classes.size());
		
		return "onlineClass/onlineClassList";
	}
	
	//온라인 강의 상세 조회
	@GetMapping("/online/{onlineClassId}")
	public String onlineView(@PathVariable Long onlineClassId, Model model, @RequestParam(value="page", defaultValue="0") int page) {
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);

		//로그인 정보 받아오기
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("로그인을 했나요?????????????????????????????? :" + userId);
		
		Collection<? extends GrantedAuthority> auth = null;
		auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		log.info("이게멀까?@@!!!!!!!!!!!!!!!!!!!!!!! 권한:" + auth);
		User user1 = null;
		
		//페이징 안한 리뷰목록
		List<OnlineReviewDTO> reviewList = onlineClassService.findOnReview(onlineClassId, "ON", Status.Y)
				.stream()
				.map(OnlineReviewDTO::new)
				.collect(Collectors.toList());
		
		//페이징 리뷰목록
		Page<Review> paging = this.onlineClassService.getList(page, onlineClassId, "ON", Status.Y);
		Page<OnlineReviewDTO> toMap = paging.map(m -> new OnlineReviewDTO(m));
		List<OnlineReviewDTO> rev = toMap.getContent();
		
		for (OnlineReviewDTO reviews : rev) {
			if(onlineClassService.getImg(reviews.getUserId().getUserId()) == "Y") {
				reviews.setImg("Y");
			}
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("rev", rev);
		
		//결제 시간
		String str = String.valueOf(onlineClassId);
		//결제 여부(기본 N, 결제 완료되어 수강 할 수 있는 상태면 Y)
		String payStatus = "N";

		//좋아요 수
		long likeCnt = 0;
		likeCnt = onlineClassService.findLikeCnt(onlineClassId);
		//내가 좋아요 상태
		String likeStatus = "N";
		
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
		
		if(userId.trim().equals("") || userId == null || userId.equals("anonymousUser")) {
			//로그인하지않음
		} else if (onlineClassService.hasInstRole()){
			//강사 로그인
		} else if (onlineClassService.hasAdminRole()){
			//admin
		}
		else {
			//강의 찜
			if(onlineClassService.findLikeMe(onlineClassId, "on", userId) > 0) {
				likeStatus = "Y";
			}
			
			//강의 결제 관련
			LocalDateTime approvedAt = null;
			
			if (!onlineClassService.findApproveAt(str, userId).isEmpty()) {
				approvedAt = onlineClassService.findApproveAt(str, userId).get(0);
			}
			
			//강의 수강 기간
			long period = onlineClass.getOnlineClassPeriod();
			//강의 들을 수 있는 날짜
			LocalDateTime endDate = null;
			
			if(approvedAt != null) {
				endDate = approvedAt.plusDays(period);
				
				if(LocalDateTime.now().isBefore(endDate) || LocalDateTime.now().isEqual(endDate)) {
					payStatus = "Y";
				}
			}
			
			Optional<User> user = onlineClassService.findByUserId(userId);
			
			user1 = user.get();
			
			
		}
		
		model.addAttribute("onlineClass", new OnlineClassView(onlineClass));
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("payStatus", payStatus);
		model.addAttribute("likeStatus", likeStatus);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("starAvg", starAvg);
		model.addAttribute("likeDto", new ClassLikeDTO());
		model.addAttribute("commentList", commentList);
		model.addAttribute("auth", auth);
		model.addAttribute("user", user1);
		
		return "onlineClass/onlineClassView";
	}
	
	//온라인 클래스 좋아요 등록
	@PostMapping("/online/likeBefore")
	@ResponseBody
	public ResponseEntity<ClassLikeDTO> onlineLike(@RequestPart(value="likeDto") ClassLikeDTO classLikeDto, Model model) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		ClassLikeDTO likeDto = null;
		
		if(userId.trim().equals("") || userId == null || userId.equals("anonymousUser"))
		{
			likeDto = new ClassLikeDTO();
			likeDto.setCode(7);
		}
		else if(onlineClassService.hasInstRole()) {
			likeDto = new ClassLikeDTO();
			likeDto.setCode(8);
		} else {
			classLikeDto.setUserId(userId);
			
			likeDto = onlineClassService.saveLike(classLikeDto);
			
		}
		
		log.info(classLikeDto.getClassId()+"classID");
		log.info(classLikeDto.getClassIden()+"iden");
		log.info(classLikeDto.getUserId()+"userID");
		
		
		return ResponseEntity.ok(likeDto);
	}
	
	
	//온라인 클래스 좋아요 취소
	@PostMapping("/online/likeAfter")
	@ResponseBody
	public ResponseEntity<Integer> onlineLikeRemove(@RequestPart(value="likeDto") ClassLikeDTO classLikeDto, Model model) {
		int result = 0;
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		classLikeDto.setUserId(userId);
		
		result = onlineClassService.removeLike(classLikeDto);
		
		log.info(classLikeDto.getClassId()+"classID");
		log.info(classLikeDto.getClassIden()+"iden");
		log.info(classLikeDto.getUserId()+"userID");
				
		return ResponseEntity.ok(result);
	}
	
	//후기 추천
	@PostMapping("/online/reviewLike")
	@ResponseBody
	public ResponseEntity<Integer> onReviewLike(@RequestPart (value="revLikeDto") OnlineReviewLikeDTO revLikeDto, Model model){
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(userId.trim().equals("") || userId == null || userId.equals("anonymousUser"))
		{
			return ResponseEntity.ok(-2); //로그인해라
		} else if(onlineClassService.hasInstRole()) {
			return ResponseEntity.ok(-3); //강사회원
		} else {
			revLikeDto.setUserId(userId);
			if(onlineClassService.findReviewLikeMe(revLikeDto.getReviewId(), revLikeDto.getUserId()) > 0) {
				return ResponseEntity.ok(0); //이미 추천함
			}
			
			if (onlineClassService.saveReviewLike(revLikeDto) != null ) {
				if(onlineClassService.updateReviewLike(revLikeDto.getReviewId()) > 0) {
					int likeCnt = onlineClassService.countReviewLike(revLikeDto.getReviewId());
					return ResponseEntity.ok(likeCnt); //추천완료
				} else {
					return ResponseEntity.ok(-1); //오류
				}
				
			} else {
				return ResponseEntity.ok(-1); //오류
			}
		}
	}

	//온라인 클래스 등록 신청(글 작성)
	@GetMapping("/online/write")
	public String onlineClassWrite(Model model) {
		Instructors inst = null;
		if(onlineClassService.hasInstRole()) {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			inst = onlineClassService.findInst(userId);
		}
		
		model.addAttribute("inst", inst);
		
		return "onlineClass/onlineClassWrite";
	}
	
	//에디터 파일 저장
	@ResponseBody
	@PostMapping("/images/onlineUpload")
	public ModelAndView onlineUpload(MultipartHttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView("jsonView");
		
		String uploadPath = photoUtil.ckUploadOnline(request);
		
		mav.addObject("uploaded", true);
		mav.addObject("url", "/images/onlineUpload" + uploadPath);
		
		return mav;
	}
	
	//게시물 등록
	@PostMapping("/online/write/post")
	@ResponseBody
	public ResponseEntity<OnlinePostDTO> onlinePost(@RequestPart(value="onlinePostDto") @Valid OnlinePostDTO onlinePostDto,
			@RequestPart(value="onlineFile") MultipartFile onlineFile, @RequestPart(value="files") List<MultipartFile> files,
			@RequestParam(value="fileContent") List<String> fileContent)
	{
		OnlinePostDTO onDto = onlineClassService.onlinePost(onlinePostDto);
		
		String path = "C:/project/sts4/SFPN/src/main/resources/static/images/onlineThumbnail";
		
		String fileName = onDto.getOnlineClassId() + ".jpg";
		
		String filepath = path + "/" + fileName;
		
		//메인이미지
		File file = new File(filepath);
		
		try 
	    {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(onlineFile.getBytes());
            bufferedOutputStream.close();
	        
	    }
	    catch (Exception e) 
        {
            throw new RuntimeException("오류가 발생했습니다.");
        } 
		
		//동영상 등록
		for (MultipartFile multipartFile : files) {
			try {
				onlineClassService.saveFile(multipartFile, onDto.getOnlineClassId(), fileContent);
			} catch (IOException e) {
				throw new RuntimeException("동영상 파일 업로드 중 오류가 발생했습니다.");
			}
		}
       return ResponseEntity.ok(onDto);   

	}
	
	//온라인 클래스 글 수정
	@GetMapping("/online/update/{onlineClassId}")
	public String onlineClassUpdate(@PathVariable Long onlineClassId, Model model) {
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		List<OnlineClassFile> classFile = onlineClassService.findFileList(onlineClassId);
		Instructors inst = null;
		
		if(onlineClassService.hasInstRole()) {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			inst = onlineClassService.findInst(userId);
			if(!onlineClass.getInstructorId().getInstructorId().equalsIgnoreCase(userId)) {
				return "redirect:/";
			}
			
			model.addAttribute("inst", inst);
		} else {
			return "redirect:/";
		}
		
		model.addAttribute("onlineClass", onlineClass);
		model.addAttribute("classFile", classFile);
		
		return "onlineClass/onlineClassUpdate";
	}
	
	@PatchMapping("/online/update/post/{onlineClassId}")
	@ResponseBody
	public ResponseEntity<Integer> onlineUpdateSave(@PathVariable final Long onlineClassId, @RequestPart(value="onlineUpdateDto") OnlineUpdateDTO params,
			@RequestPart(value="onlineFile", required = false) MultipartFile onlineFile, @RequestPart(value="files", required = false) List<MultipartFile> files,
			@RequestParam(value="fileContent") List<String> fileContent, @RequestParam(value="idxList", required = false) List<Integer> idxList) {
		
		if(onlineClassService.onlineUpdate(onlineClassId, params) > 0) { //글 수정
			
			//대표 이미지 파일이 있으면 수정
			if(onlineFile != null) {
				String path = "C:/project/sts4/SFPN/src/main/resources/static/images/onlineThumbnail";
				String fileName = onlineClassId + ".jpg";
				String filepath = path + "/" + fileName;
				
				//기존 이미지 삭제
				File file = new File(filepath);
				
				if (!file.delete()) {
					return ResponseEntity.ok(-1); // 파일 삭제 실패
				}
				
				file = new File(filepath);
				
				try {
		            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		            bufferedOutputStream.write(onlineFile.getBytes());
		            bufferedOutputStream.close();
			        
			    } catch (Exception e) {
		            throw new RuntimeException("오류가 발생했습니다.");
		        }

			}
			
			//동영상 첨부파일 수정
			if(files != null) {
				int i = 0;
				for (MultipartFile multipartFile : files) {
					try {
						onlineClassService.updateFile(multipartFile, onlineClassId, idxList, i);
					} catch (IOException e) {
						throw new RuntimeException("동영상 파일 업로드 중 오류가 발생했습니다.");
					}
					i++;
				}
			}
			
			//동영상 설명 수정
			if(fileContent != null) {
				long j = 1;
				for (String cont : fileContent) {
					onlineClassService.contentUpdate(onlineClassId, j, cont);
					log.info("되고있니?"+j+"ㅠㅠ??"+cont);
					j++;
				}
			}
			
			return ResponseEntity.ok(1); //정상수행
        }
        return ResponseEntity.ok(0);
    }
	
	//동영상 파일 삭제
	@DeleteMapping("/online/update/post/{onlineClassId}/{onlineFileId}")
	@ResponseBody
	public ResponseEntity<Integer> videoDelete(@PathVariable final long onlineClassId, @PathVariable long onlineFileId) {
		//로컬에서 지워야되는데말이야
		try {
			onlineClassService.deleteFile(onlineClassId, onlineFileId);
		} catch (IOException e) {
			return ResponseEntity.ok(0);
		}
		return ResponseEntity.ok(1);
	}
}