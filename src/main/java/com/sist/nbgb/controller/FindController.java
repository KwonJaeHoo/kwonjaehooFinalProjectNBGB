package com.sist.nbgb.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.FindIdDto;
import com.sist.nbgb.dto.FindPasswordDto;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.service.FindService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FindController 
{
	private final FindService findService;
	
	@GetMapping("/findid")
	public String findId()
	{
		return "find/findId";
	}
	
	@GetMapping("/findpassword")
	public String findPassword()
	{
		return "find/findPassword";
	}
	
	@PostMapping("/findid/user")
	public ResponseEntity<UserIdCheckDto> findUserId(@RequestBody @Valid FindIdDto findIdDto)
	{
		return ResponseEntity.ok(findService.findUserId(findIdDto));
	}
	
	@PostMapping("/findid/instructor")
	public ResponseEntity<InstructorIdCheckDto> findInstructorId(@RequestBody @Valid FindIdDto findIdDto)
	{
		return ResponseEntity.ok(findService.findInstructorId(findIdDto));
	}
	
	@PostMapping("/findpassword/user")
	public ResponseEntity<EmailCheckDto> findUserPassword(@RequestBody @Valid FindPasswordDto findPasswordDto)
	{
		return ResponseEntity.ok(findService.findUserPassword(findPasswordDto));
	}

	@PostMapping("/findpassword/instructor")
	public ResponseEntity<EmailCheckDto> findInstructorPassword(@RequestBody @Valid FindPasswordDto findPasswordDto)
	{
		return ResponseEntity.ok(findService.findInstructorPassword(findPasswordDto));
	}

	@PostMapping("findpassword/user/changepassword")
	public ResponseEntity<String> userChangePassword(@RequestBody @Valid FindPasswordDto findPasswordDto) throws UnsupportedEncodingException, MessagingException
	{	
		return ResponseEntity.ok(findService.userChangePassword(findPasswordDto));
	}
	
	@PostMapping("findpassword/instructor/changepassword")
	public ResponseEntity<String> instructorChangePassword(@RequestBody @Valid FindPasswordDto findPasswordDto)  throws UnsupportedEncodingException, MessagingException
	{
		return ResponseEntity.ok(findService.instructorChangePassword(findPasswordDto));
	}
}
