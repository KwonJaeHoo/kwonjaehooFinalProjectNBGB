package com.sist.nbgb.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorsDto;
import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.service.EmailService;
import com.sist.nbgb.service.SignupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController 
{
	private final SignupService signupService;	
	private final EmailService emailService;
	
	@GetMapping("/signup")
	public String signup()
	{
		return "/signup/signup";
	}
	
	@PostMapping("/signup/emailCheck")	
    @ResponseBody
    public ResponseEntity<String> EmailCheck(@RequestBody EmailCheckDto emailCheckDto) throws MessagingException, UnsupportedEncodingException 
	{
        return ResponseEntity.ok(emailService.sendEmail(emailCheckDto.getEmail()));
    }
	
	@GetMapping("/signup/user")
	public String userSignup()
	{
		return "/signup/signupUser";
	}
	
	@PostMapping("/signup/user/idcheck")
	@ResponseBody
	public ResponseEntity<Boolean> userSignupDuplicateId(@RequestBody UserIdCheckDto userIdCheckDto)
	{	
		//중복되는 경우 true, 중복되지 않은경우 false return
		return ResponseEntity.ok(signupService.userSignupDuplicateId(userIdCheckDto));
	}
	
	@PostMapping("/signup/user")
	@ResponseBody
	public ResponseEntity<UserDto> userSignupProcess(@RequestBody @Valid UserDto userDto) throws RuntimeException
	{
		return ResponseEntity.ok(signupService.userSignup(userDto));	
	}
	
	
	
	@GetMapping("/signup/instructor")
	public String instructorSignup()
	{
		return "/signup/signupInstructor";
	}
		
	@PostMapping("/signup/instructor/idcheck")
	@ResponseBody
	public ResponseEntity<Boolean> instructorSignupDuplicateId(@RequestBody InstructorIdCheckDto instructorIdCheckDto)
	{	
		//중복되는 경우 true, 중복되지 않은경우 false return
		return ResponseEntity.ok(signupService.instructorSignupDuplicateId(instructorIdCheckDto));
	}
	
	@PostMapping("/signup/instructor")
	@ResponseBody
	public ResponseEntity<InstructorsDto> instructorSignupProcess(@RequestPart(value="instructorDto") @Valid InstructorsDto instructorsDto, @RequestPart(value="instructorImageFile") MultipartFile instructorImageFile) throws Exception
	{
	    String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\instructor";
	    String filename = instructorsDto.getInstructorId() + ".png"; // 기본 파일명
	    String filepath = path + "/" + filename;
		
        File file = new File(filepath);
        try 
        {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(instructorImageFile.getBytes());
            bufferedOutputStream.close();
        
        }
        catch (Exception e) 
        {
            throw new RuntimeException("오류가 발생했습니다.");
        } 
	    
	    return ResponseEntity.ok(signupService.instructorSignup(instructorsDto));	
	}
}