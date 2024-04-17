package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController 
{
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
}
