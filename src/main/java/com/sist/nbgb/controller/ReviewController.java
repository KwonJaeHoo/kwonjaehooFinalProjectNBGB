package com.sist.nbgb.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.OnlineClassListDTO;
import com.sist.nbgb.dto.OnlineReviewCommentDTO;
import com.sist.nbgb.dto.OnlineReviewDTO;
import com.sist.nbgb.dto.UserReviewRequestDTO;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.service.OnlineClassService;
import com.sist.nbgb.service.OnlineReviewService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    
	private final UserService userService;
	private final OnlineClassService onlineClassService;
	private final OnlineReviewService onlineReviewService;
	
	/*온라인 리뷰 작성*/
    @GetMapping("/user/userReviewWrite/{classId}/{classIden}")
    public String reviewWrite(Model model, Principal principal, @PathVariable Long classId, @PathVariable(value="classIden") String classIden){
    	User user = userService.findUserById(principal.getName());
    	if(classIden.equals("ON")) {
    		OnlineClassListDTO onlineClass = new OnlineClassListDTO(onlineClassService.findById(classId));
    		
    		model.addAttribute("userNickname", user.getUserNickname());
    		model.addAttribute("class", onlineClass);
    	}else {
    		
    	}
    	model.addAttribute(classIden, classIden);
    	return "mypage/review/userReviewWrite";
    }
    
    /*온오프 둘 다 되게 수정*/
    @ResponseBody
    @PostMapping("/user/userOnlineReview/upload/{classIden}")
    public ResponseEntity<UserReviewRequestDTO> reviewUpload(Principal principal, @PathVariable(value="classIden") String classIden,
    		@RequestParam(value="rating") String rating, @RequestParam(value="reviewContents") String reviewContents, @RequestParam(value="classId") Long classId){
    	User user = userService.findUserById(principal.getName());
    	UserReviewRequestDTO reviewUpload = null;
    	if(user != null) {
    		if(classIden.equals("ON")) {
    			reviewUpload = UserReviewRequestDTO.builder()
    					.classId(classId)
    					.classIden(classIden)
    					.reviewContent(reviewContents)
    					.reviewLikeCnt(Long.valueOf(0))
    					.reviewRating(Long.valueOf(rating))
    					.reviewRegdate(LocalDateTime.now())
    					.reviewStatus(Status.Y)
    					.userId(user)
    					.build();
    			onlineReviewService.uploadOnlineReview(reviewUpload);
    		}
    	}
    	return ResponseEntity.ok().body(reviewUpload);
    }
    
    /*온오프라인 리뷰 조회*/
    @GetMapping("/user/userReviewView/{classId}/{classIden}")
    public String reviewView(Model model, Principal principal, @PathVariable(value="classId") Long classId, @PathVariable(value="classIden")String classIden) {
    	User user = userService.findUserById(principal.getName());
    	if(user.getAuthority().equals(Role.ROLE_USER)) {
	    	if(classIden.equals("ON")) {
	    		OnlineClassListDTO onlineClass = new OnlineClassListDTO(onlineClassService.findById(classId));
	    		OnlineReviewDTO review = onlineReviewService.viewReview(user, classId, classIden);
	    		//OnlineReviewCommentDTO comment = onlineReviewService.viewReviewComment(userReview);
	    		model.addAttribute("class", onlineClass);
	    		model.addAttribute("review", review);
	    		//model.addAttribute("comment", comment);
	    	}
    	}
    	model.addAttribute("userNickname", user.getUserNickname());
    	return "mypage/review/userReviewView";
    }
    
    /*오프라인 리뷰 작성*/
}
