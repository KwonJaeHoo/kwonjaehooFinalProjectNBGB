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
	//css 수정
	@GetMapping("/login2")
	public String login2()
	{
		return "login/login2";
	}
	@GetMapping("/signup2")
	public String signup2()
	{
		return "signup/signup2";
	}
	@GetMapping("/signupInstructor2")
	public String signupInstructor2()
	{
		return "signup/signupInstructor2";
	}
	@GetMapping("/signupUser2")
	public String signupUser2()
	{
		return "signup/signupUser2";
	}
}