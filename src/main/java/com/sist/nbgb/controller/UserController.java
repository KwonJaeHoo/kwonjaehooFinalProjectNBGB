package com.sist.nbgb.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController 
{
	private final UserService userService;
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup()
	{
		return "signup";
	}	
	
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<UserDto> signupProcess(@RequestBody @Valid UserDto userDto)
	{
		return ResponseEntity.ok(userService.SignUp(userDto));	
	}
	

	
}