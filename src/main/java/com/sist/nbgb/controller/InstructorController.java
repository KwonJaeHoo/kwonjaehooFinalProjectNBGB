package com.sist.nbgb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.service.InstructorsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InstructorController 
{
	private final InstructorsService instructorsService;
	
	@GetMapping("/instructor/test")
	public ResponseEntity<String> instTest()
	{
    	String userid = SecurityContextHolder.getContext().getAuthentication().getName();
    	return ResponseEntity.ok(userid);
	}
}