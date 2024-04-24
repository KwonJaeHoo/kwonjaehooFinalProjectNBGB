package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.nbgb.service.InstructorsService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController 
{
	private final UserService userService;
	private final InstructorsService instructorService;
	
	@GetMapping("/login")
	public String login()
	{
		return "login/login";
	}
}