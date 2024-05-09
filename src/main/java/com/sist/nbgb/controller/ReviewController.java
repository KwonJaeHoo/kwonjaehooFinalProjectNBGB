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
import com.sist.nbgb.dto.UserReviewRequestDTO;
import com.sist.nbgb.entity.User;
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
	
    @GetMapping("/user/userReview/{classId}")
    public String reviewWrite(Model model, Principal principal, @PathVariable Long classId){
    	User user = userService.findUserById(principal.getName());
    	
    	OnlineClassListDTO onlineClass = new OnlineClassListDTO(onlineClassService.findById(classId));
    	
    	model.addAttribute("userNickname", user.getUserNickname());
    	model.addAttribute("class", onlineClass);
    	return "mypage/review/userReview";
    }
    
    @ResponseBody
    @PostMapping("/user/userReview/upload")
    public ResponseEntity<UserReviewRequestDTO> reviewUpload(Principal principal, @RequestParam(value="rating") String rating,
    		@RequestParam(value="reviewContents") String reviewContents, @RequestParam(value="classId") Long classId){
    	User user = userService.findUserById(principal.getName());
    	UserReviewRequestDTO reviewUpload = null;
    	if(user != null) {
    		reviewUpload = UserReviewRequestDTO.builder()
    						.classId(classId)
    						.classIden("ON")
    						.reviewContent(reviewContents)
    						.reviewLikeCnt(Long.valueOf(0))
    						.reviewRating(Long.valueOf(rating))
    						.reviewRegdate(LocalDateTime.now())
    						.reviewStatus(Status.U)
    						.userId(user)
    						.build();
    		onlineReviewService.uploadOnlineReview(reviewUpload);
    	}
    	return ResponseEntity.ok().body(reviewUpload);
    }
}
