package com.sist.nbgb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
    @GetMapping("/instructor/mypage/{id}")
    public String mypage(Model model, @PathVariable String id)
    {
    	model.addAttribute("id", id);
    	
    	return "mypage/mypage";
    }
}