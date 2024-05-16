package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ErrorController
{
	@GetMapping("/403")
	public String showAccessDeniedPage() 
	{
	    return "error/403";
	}
}
