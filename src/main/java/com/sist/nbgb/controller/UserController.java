package com.sist.nbgb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.service.UserService;
import com.sist.nbgb.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController
{
	private final UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<UserIdCheckDto> findUserId()
	{
		return ResponseEntity.ok(userService.findByUserId(SecurityUtil.getCurrentUserId()));
	}
	
	@GetMapping("/user/{userid}")
	public ResponseEntity<UserIdCheckDto> findbyUserId(@PathVariable UserIdCheckDto userIdCheckDto)
	{
		return ResponseEntity.ok(userService.findByUserId(userIdCheckDto.getUserId()));
	}
}
