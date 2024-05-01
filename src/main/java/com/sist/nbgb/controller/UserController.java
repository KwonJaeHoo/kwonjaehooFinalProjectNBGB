package com.sist.nbgb.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController
{
	private final UserService userService;
    
    @GetMapping("/test")
    public ResponseEntity<String> getMyUserInfo() 
    {
    	String userid = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities()); 
    	return ResponseEntity.ok(userid);
    }
    
    @GetMapping("/mypage/{id}")
    public String mypage(Model model, @PathVariable String id)
    {
    	model.addAttribute("id", id);
    	
    	return "mypage/mypage";
    }
}