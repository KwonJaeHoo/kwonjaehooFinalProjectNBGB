package com.sist.nbgb.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nbgb.dto.EmailChangeDto;
import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.LoginDto;
import com.sist.nbgb.dto.NicknameChangeDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto1;
import com.sist.nbgb.dto.OfflinePaymentCancelDto;
import com.sist.nbgb.dto.OnlinePaymentApproveDto;
import com.sist.nbgb.dto.OnlinePaymentCancelDto;
import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.dto.PhoneChangeDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.dto.UserReviewDTO;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.service.EmailService;
import com.sist.nbgb.service.OfflineService;
import com.sist.nbgb.service.OnlineClassService;
import com.sist.nbgb.service.OnlineReviewService;
import com.sist.nbgb.service.SignupService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController
{
	private final UserService userService;
	private final OnlineClassService onlineClassService;
	private final OfflineService offlineService;
	private final PasswordEncoder passwordEncoder;
	
	private final SignupService signupService;
	private final EmailService emailService;
	private final OnlineReviewService onlineReviewService;
        
    @GetMapping("/mypage/{id}/info")
    public String mypageUserInfo(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		UserInfoDto userInfoDto = userService.findByUserId(id);
        	model.addAttribute("userInfoDto", userInfoDto);
        	
    		String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
    	    String filename = userInfoDto.getUserId() + ".png"; // 기본 파일명
    	    String filepath = path + "/" + filename;
    		
            File file = new File(filepath);
            
    		if(file.exists())
    		{
    			model.addAttribute("userFileDto", userInfoDto.getUserId());
    		}
        	else
        	{
        		model.addAttribute("userFileDto", "userFileDtoIsNull");
        	}
    	}
    	
    	return "mypage/mypageInfo";
    }
    
    @GetMapping("/mypage/{id}/modify")
    public String mypageUserInfoModify(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		UserInfoDto userInfoDto = userService.findByUserId(id);
        	model.addAttribute("userInfoDto", userInfoDto);
        	
    		String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
    	    String filename = userInfoDto.getUserId() + ".png"; // 기본 파일명
    	    String filepath = path + "/" + filename;
    		
            File file = new File(filepath);
            
    		if(file.exists())
    		{
    			model.addAttribute("userFileDto", userInfoDto.getUserId());
    		}
        	else
        	{
        		model.addAttribute("userFileDto", "userFileDtoIsNull");
        	}
    	}
    	
    	
    	
    	return "mypage/mypageModify";
    }
    
    
    @PostMapping("/mypage/info/nowuserpasswordcheck")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPasswordCheck(@RequestBody LoginDto loginDto)
    {  	
    	UserInfoDto userInfoDto = userService.findByUserId(loginDto.getId());
    	
    	if(passwordEncoder.matches(loginDto.getPassword(), userInfoDto.getUserPassword()))
    	{
    		return ResponseEntity.ok(200);
    	}
    	
    	return ResponseEntity.ok(400);
    }
    
    @PostMapping("/mypage/info/userpassword")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPassword(@RequestBody LoginDto loginDto)
    {
    	System.out.println(loginDto.getId());
    	System.out.println(loginDto.getPassword());
    	return ResponseEntity.ok(userService.changeUserPassword(loginDto.getId(), loginDto.getPassword()));
    }
    
    @PostMapping("/mypage/info/usernickname")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoNickname(@RequestBody NicknameChangeDto nicknameChangeDto)
    {
    	return ResponseEntity.ok(userService.changeUserNickname(nicknameChangeDto.getId(), nicknameChangeDto.getNickname()));
    }
    
	@PostMapping("/mypage/info/emailcheck")	
    @ResponseBody
    public ResponseEntity<Boolean> userEmailCheck(@RequestBody EmailCheckDto emailCheckDto)
	{
        return ResponseEntity.ok(signupService.userSignupDuplicateEmail(emailCheckDto));
    }
    
	@PostMapping("/mypage/info/emailsend")	
    @ResponseBody
    public ResponseEntity<String> EmailSend(@RequestBody EmailCheckDto emailCheckDto) throws MessagingException, UnsupportedEncodingException 
	{
        return ResponseEntity.ok(emailService.sendEmail(emailCheckDto.getEmail()));
    }
	
    @PostMapping("/mypage/info/useremail")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoEmail(@RequestBody EmailChangeDto emailChangeDto)
    {
    	return ResponseEntity.ok(userService.changeUserEmail(emailChangeDto.getId(), emailChangeDto.getEmail()));
    }
    

    @PostMapping("/mypage/info/userphone")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoPhone(@RequestBody PhoneChangeDto phoneChangeDto)
    {
    	return ResponseEntity.ok(userService.changeUserPhone(phoneChangeDto.getId(), phoneChangeDto.getPhone()));
    }
    
    
    @PostMapping("/mypage/info/userfile")
	@ResponseBody
	public ResponseEntity<Object> mypageUserInfoFile(@RequestPart(value="userId") @Valid UserIdCheckDto userIdCheckDto, @RequestPart(value="userImageFile") MultipartFile userImageFile) throws Exception
	{
    	String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
	    String filename = userIdCheckDto.getUserId() + ".png"; // 기본 파일명
	    String filepath = path + "/" + filename;
		
        File file = new File(filepath);
        
		if(file.exists())
		{
			file.delete();
    	}

        try 
        {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(userImageFile.getBytes());
            bufferedOutputStream.close();
            
            return ResponseEntity.ok(200);
        }
        catch (Exception e) 
        {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }
    
    @PostMapping("/mypage/info/userfiledelete")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoFileDelete(@RequestBody UserIdCheckDto userIdCheckDto)
    {
    	String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
	    String filename = userIdCheckDto.getUserId() + ".png"; // 기본 파일명
	    String filepath = path + "/" + filename;
		
        File file = new File(filepath);
        file.delete();
    	
    	return ResponseEntity.ok(200);
    }
    
    
    @GetMapping("/mypage/{id}/signout")
    public String mypageUserInfoSignout(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		UserInfoDto userInfoDto = userService.findByUserId(id);
        	model.addAttribute("userInfoDto", userInfoDto);
    	}
    	
    	return "mypage/mypageSignout";
    }
    
    @PostMapping("/mypage/info/signout")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoSignout(@RequestBody UserIdCheckDto userIdCheckDto)
    {
    	return ResponseEntity.ok(userService.signoutUser(userIdCheckDto.getUserId()));
    }
    

    @GetMapping("/mypage/{id}/payment")
    public String mypageUserPayment(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		List<OnlinePaymentApproveDto> onlinePaymentApproveDto = userService.userOnlineApproveFindAll(id)
    				.stream().map(OnlinePaymentApproveDto::new).collect(Collectors.toList());
    		
    		System.out.println(onlinePaymentApproveDto);
    		System.out.println(onlinePaymentApproveDto.size());
    		
    		List<OfflinePaymentApproveDto1> offlinePaymentApproveDto = userService.userOfflineApproveFindAll(id)
    				.stream().map(OfflinePaymentApproveDto1::new).collect(Collectors.toList());
    		
    		System.out.println(offlinePaymentApproveDto);
    		
    		List<OnlinePaymentCancelDto> onlinePaymentCancelDto = userService.userOnlineCancelFindAll(id)
    				.stream().map(OnlinePaymentCancelDto::new).collect(Collectors.toList());
    		
    		System.out.println(onlinePaymentCancelDto);
    		
    		List<OfflinePaymentCancelDto> offlinePaymentCancelDto = userService.userOfflineCancelFindAll(id)
    				.stream().map(OfflinePaymentCancelDto::new).collect(Collectors.toList());
        	
    		System.out.println(offlinePaymentCancelDto);
    		
    		//onlineApprove
    		if(onlinePaymentApproveDto.isEmpty())
    		{
    			model.addAttribute("onlinePaymentApproveDto", "onlinePaymentApproveDtoIsNull");
    		}
    		else
    		{
    			model.addAttribute("onlinePaymentApproveDto", onlinePaymentApproveDto);
    		}
    		
    		//offlineApprove
    		if(offlinePaymentApproveDto.isEmpty())
    		{
    			model.addAttribute("offlinePaymentApproveDto", "offlinePaymentApproveDtoIsNull");
    		}
    		else
    		{
    			model.addAttribute("offlinePaymentApproveDto", offlinePaymentApproveDto);
    		}
    		
    		//onlineCancel
    		if(onlinePaymentCancelDto.isEmpty())
    		{
    			model.addAttribute("onlinePaymentCancelDto", "onlinePaymentCancelDtoIsNull");
    		}
    		else
    		{
    			model.addAttribute("onlinePaymentCancelDto", onlinePaymentCancelDto);
    		}
    		
    		//offlineCancel
    		if(offlinePaymentCancelDto.isEmpty())
    		{
    			model.addAttribute("offlinePaymentCancelDto", "offlinePaymentCancelDtoIsNull");
    		}
    		else
    		{
    			model.addAttribute("offlinePaymentCancelDto", offlinePaymentCancelDto);
    		}
    		
    		model.addAttribute("localDateTime", LocalDateTime.now());
    	}
    	
    	return "mypage/mypagePayment";
    }
    
    
    @GetMapping("/mypage/{id}/onlinelecturelist")
    public String mypageUserOnlineLecture(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		User user = userService.findUserById(id);
    		List<OnlinePaymentClassListDTO> classes = onlineClassService.userLectureList(id);
    		
    		LocalDateTime now = LocalDateTime.now();
    		
    		Map<Integer, Integer> map = new HashMap<>();
			for (OnlinePaymentClassListDTO reviewChk : classes) {
				map.put(Integer.parseInt(String.valueOf(reviewChk.getOnlineClassId())),
						onlineReviewService.exsitsOnlineReview(user, reviewChk.getOnlineClassId(), "ON"));
			}
    		/*재결재 시 리뷰 작성 여부 체크 다시*/
        	model.addAttribute("classes", classes);
        	model.addAttribute("now", now);
        	model.addAttribute("reviewChk", map);
    	}
    	
    	return "mypage/mypageOnlineLectureList";
    }
    
    @GetMapping("/mypage/{id}/offlinelecturelist")
    public String mypageUserOfflineLecture(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		User user = userService.findUserById(id);
    		
    	}
    	
    	return "mypage/mypageOfflineLectureList";
    }
    
    @GetMapping("/mypage/{id}/onlinelikelist")
    public String mypageUserOnlineLike(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    	}
    	
    	return "mypage/mypageOnlineLikeList";
    }
    
    @GetMapping("/mypage/{id}/offlinelikelist")
    public String mypageUserOfflineLike(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    	}
    	
    	return "mypage/mypageOfflineLikeList";
    }
    
    @GetMapping("/mypage/{id}/review")
    public String mypageUserReview(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		List<UserReviewDTO> userReviewDto = userService.userReviewFindAll(id)
    				.stream()
    				.map(UserReviewDTO::new)
    				.collect(Collectors.toList());
    		
//    		List<OnlineClassTitleDto> onlineClassTitleDto = onlineClassService.mypageTitle()
//    				.stream().map(OnlineClassTitleDto::new).collect(Collectors.toList());
    				
    		
    	//	List<OfflineResponse> offlineClassListDto = offlineService.
    		
    		
        	model.addAttribute("userReviewDto", userReviewDto);
    	}
    	
    	return "mypage/mypageReview";
    }
    
    @GetMapping("/mypage/{id}/reference")
    public String mypageUserReference(Model model, @PathVariable String id)
    {
    	if(id != null)
    	{
    		List<UserReviewDTO> userReviewDto = userService.userReviewFindAll(id)
    				.stream()
    				.map(UserReviewDTO::new)
    				.collect(Collectors.toList());
    		
//    		List<OnlineClassTitleDto> onlineClassTitleDto = onlineClassService.mypageTitle()
//    				.stream().map(OnlineClassTitleDto::new).collect(Collectors.toList());
    				
    		
    	//	List<OfflineResponse> offlineClassListDto = offlineService.
    		
    		
        	model.addAttribute("userReviewDto", userReviewDto);
    	}
    	
    	return "mypage/mypageReference";
    }
}