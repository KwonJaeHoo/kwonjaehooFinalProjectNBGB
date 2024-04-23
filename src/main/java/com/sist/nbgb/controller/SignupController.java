package com.sist.nbgb.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorsDto;
import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.service.EmailService;
import com.sist.nbgb.service.InstructorsService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController 
{
	private final UserService userService;
	
	private final InstructorsService instructorsService;
	
	private final EmailService emailService;
	
	@GetMapping("/signup")
	public String signup()
	{
		return "/signup/signup";
	}
	
	@GetMapping("/signup_user")
	public String userSignup()
	{
		return "/signup/signupUser";
	}
	
	@GetMapping("/signup_instructor")
	public String instructorSignup()
	{
		return "/signup/signupInstructor";
	}
	
	@PostMapping("/signup_emailCheck")	
    @ResponseBody
    public ResponseEntity<String> EmailCheck(@RequestBody EmailCheckDto emailCheckDto) throws MessagingException, UnsupportedEncodingException 
	{
        return ResponseEntity.ok(emailService.sendEmail(emailCheckDto.getEmail()));
    }
	
	@PostMapping("/signup_user_idcheck")
	@ResponseBody
	public ResponseEntity<Boolean> userSignupDuplicateId(@RequestBody UserIdCheckDto userIdCheckDto)
	{	
		//중복되는 경우 true, 중복되지 않은경우 false return
		return ResponseEntity.ok(userService.userSignupDuplicateId(userIdCheckDto));
	}
	
	@PostMapping("/signup_user")
	@ResponseBody
	public ResponseEntity<UserDto> userSignupProcess(@RequestBody @Valid UserDto userDto) throws RuntimeException
	{
		return ResponseEntity.ok(userService.userSignup(userDto));	
	}
	
	@PostMapping("/signup_instructor_idcheck")
	@ResponseBody
	public ResponseEntity<Boolean> instructorSignupDuplicateId(@RequestBody InstructorIdCheckDto instructorIdCheckDto)
	{	
		//중복되는 경우 true, 중복되지 않은경우 false return
		return ResponseEntity.ok(instructorsService.instructorSignupDuplicateId(instructorIdCheckDto));
	}
	
	@PostMapping("/signup_instructor")
	@ResponseBody
	public ResponseEntity<InstructorsDto> instructorSignupProcess(@RequestBody @Valid InstructorsDto instructorsDto)
	{
		
		return ResponseEntity.ok(instructorsService.instructorSignup(instructorsDto));	
	}
}