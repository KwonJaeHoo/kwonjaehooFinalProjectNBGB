package com.sist.nbgb.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController
{
	private final UserService userService;
    
    @GetMapping("/user/test")
    public ResponseEntity<String> getMyUserInfo() 
    {
    	String userid = SecurityContextHolder.getContext().getAuthentication().getName();
    	return ResponseEntity.ok(userid);
    }
}