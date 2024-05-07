package com.sist.nbgb.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nbgb.dto.BankAccountChangeDto;
import com.sist.nbgb.dto.CategoryChangeDto;
import com.sist.nbgb.dto.EmailChangeDto;
import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.InstructorInfoDto;
import com.sist.nbgb.dto.InstructorsDto;
import com.sist.nbgb.dto.LoginDto;
import com.sist.nbgb.dto.NicknameChangeDto;
import com.sist.nbgb.dto.PhoneChangeDto;
import com.sist.nbgb.dto.UserFileDto;
import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.service.EmailService;
import com.sist.nbgb.service.InstructorsService;
import com.sist.nbgb.service.SignupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/instructor")
public class InstructorController 
{
	private final InstructorsService instructorsService;
	private final PasswordEncoder passwordEncoder;
	
	private final SignupService signupService;
	private final EmailService emailService;
	    
    @GetMapping("/mypage/{id}/info")
    public String mypageInstructorInfo(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		InstructorInfoDto instructorDto = instructorsService.findByInstructorId(id);
        	model.addAttribute("instructorDto", instructorDto);
    	}
    	return "mypage/mypageInfo";
    }
    
    @GetMapping("/mypage/{id}/modify")
    public String mypageInstructorInfoModify(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		InstructorInfoDto instructorInfoDto = instructorsService.findByInstructorId(id);
        	model.addAttribute("instructorInfoDto", instructorInfoDto);
    	}
    	return "mypage/mypageModify";
    }
    
    @PostMapping("/mypage/info/nowinstructorpasswordcheck")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPasswordCheck(@RequestBody LoginDto loginDto)
    {  	
    	InstructorInfoDto instructorInfoDto = instructorsService.findByInstructorId(loginDto.getId());
    	
    	if(passwordEncoder.matches(loginDto.getPassword(), instructorInfoDto.getInstructorPassword()))
    	{
    		return ResponseEntity.ok(200);
    	}
    	
    	return ResponseEntity.ok(400);
    }
    
    @PostMapping("/mypage/info/instructorpassword")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPassword(@RequestBody LoginDto loginDto)
    {
    	System.out.println(loginDto.getId());
    	System.out.println(loginDto.getPassword());
    	return ResponseEntity.ok(instructorsService.changeInstructorPassword(loginDto.getId(), loginDto.getPassword()));
    }
    
    @PostMapping("/mypage/info/instructornickname")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoNickname(@RequestBody NicknameChangeDto nicknameChangeDto)
    {
    	return ResponseEntity.ok(instructorsService.changeInstructorNickname(nicknameChangeDto.getId(), nicknameChangeDto.getNickname()));
    }
    
	@PostMapping("/mypage/info/emailcheck")	
    @ResponseBody
    public ResponseEntity<Boolean> userEmailCheck(@RequestBody EmailCheckDto emailCheckDto)
	{
        return ResponseEntity.ok(signupService.instructorSignUpDuplicateEmail(emailCheckDto));
    }
    
	@PostMapping("/mypage/info/emailsend")	
    @ResponseBody
    public ResponseEntity<String> EmailSend(@RequestBody EmailCheckDto emailCheckDto) throws MessagingException, UnsupportedEncodingException 
	{
        return ResponseEntity.ok(emailService.sendEmail(emailCheckDto.getEmail()));
    }
	
    @PostMapping("/mypage/info/instructoremail")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoEmail(@RequestBody EmailChangeDto emailChangeDto)
    {
    	return ResponseEntity.ok(instructorsService.changeInstructorEmail(emailChangeDto.getId(), emailChangeDto.getEmail()));
    }
    

    
    @PostMapping("/mypage/info/instructorphone")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPhone(@RequestBody PhoneChangeDto phoneChangeDto)
    {
    	return ResponseEntity.ok(instructorsService.changeInstructorPhone(phoneChangeDto.getId(), phoneChangeDto.getPhone()));
    }
    
    @PostMapping("/mypage/info/instructorbankaccount")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoBankAccount(@RequestBody BankAccountChangeDto bankAccountChangeDto)
    {
    	return ResponseEntity.ok(instructorsService.changeInstructorBankAccount(bankAccountChangeDto.getId(), bankAccountChangeDto.getBank(), bankAccountChangeDto.getAccount()));
    }
    
    @PostMapping("/mypage/info/instructorcategory")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoCategory(@RequestBody CategoryChangeDto categoryChangeDto)
    {
    	return ResponseEntity.ok(instructorsService.changeInstructorPhone(categoryChangeDto.getId(), categoryChangeDto.getCategory()));
    }
    
    
//    @PostMapping("/mypage/{id}/info/userfile")
//    public ResponseEntity<UserFileDto> mypageUserInfoFile(InstructorsDto instructorsDto, MultipartFile instructorImageFile) throws Exception
//    {
//    	return ResponseEntity.ok(null);
//    }
    
    
    @GetMapping("/mypage/{id}/lecture")
    public String mypageInstructorLecture(Model model, @PathVariable String id)
    {
    	model.addAttribute("id", id);
    	
    	return "mypage/mypageLecture";
    }
    
    @GetMapping("/mypage/{id}/review")
    public String mypageInstructorReview(Model model, @PathVariable String id)
    {
    	model.addAttribute("id", id);
    	
    	return "mypage/mypageReview";
    }
    
    
}