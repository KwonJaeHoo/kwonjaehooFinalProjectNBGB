package com.sist.nbgb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.service.InstructorsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InstructorController 
{
	private final InstructorsService instructorsService;
	
	@GetMapping("/instructor/intest")
	public ResponseEntity<String> instTest()
	{
		return ResponseEntity.ok("test");
	}
}