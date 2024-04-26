package com.sist.nbgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OnlineClassPlayController 
{
	
	//온라인 강의 재생 페이지
	@GetMapping("/onlinePlay")
	public String onlinePlay() 
	{
		return "onlineClass/onlineClassPlay";
	}
}
