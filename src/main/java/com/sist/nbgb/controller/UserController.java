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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.EmailChangeDto;
import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.KakaoPartnerOrderIdDto;
import com.sist.nbgb.dto.KakaoPaymentCancelDto;
import com.sist.nbgb.dto.LoginDto;
import com.sist.nbgb.dto.NicknameChangeDto;
import com.sist.nbgb.dto.OfflineClassPaymentListDTO;
import com.sist.nbgb.dto.OfflinePaymentApproveDto1;
import com.sist.nbgb.dto.OfflinePaymentCancelDto;
import com.sist.nbgb.dto.OnlinePaymentApproveDto;
import com.sist.nbgb.dto.OnlinePaymentCancelDto;
import com.sist.nbgb.dto.OnlinePaymentClassListDTO;
import com.sist.nbgb.dto.PhoneChangeDto;
import com.sist.nbgb.dto.ReferenceDto2;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.kakao.KakaoPayCancel;
import com.sist.nbgb.service.EmailService;
import com.sist.nbgb.service.KakaoService;
import com.sist.nbgb.service.ReviewService;
import com.sist.nbgb.service.SignupService;
import com.sist.nbgb.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController
{
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final SignupService signupService;
	private final EmailService emailService;
	private final ReviewService reviewService;
	private final KakaoService kakaoService;
        
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
    	return ResponseEntity.ok(userService.changeUserPassword(loginDto.getId(), loginDto.getPassword()));
    }
    
    @PostMapping("/mypage/info/usernickname")
    @ResponseBody
    public ResponseEntity<Object> mypageUserInfoNickname(@RequestBody NicknameChangeDto nicknameChangeDto, HttpServletRequest httpServletRequest)
    {
    	return ResponseEntity.ok(userService.changeUserNickname(nicknameChangeDto.getId(), nicknameChangeDto.getNickname(), httpServletRequest));
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
    public String mypageUserPayment(Model model, @PathVariable String id, @RequestParam(value="page", defaultValue="0") int page,
    		@RequestParam(value="page2", defaultValue="0") int page2,
    		@RequestParam(value="page3", defaultValue="0") int page3,
    		@RequestParam(value="page4", defaultValue="0") int page4)
    {
    	if(id != null)
    	{
    		//온라인 결제 내역
    		List<OnlinePaymentApproveDto> onlinePaymentApproveDto = userService.userOnlineApproveFindAll(id)
    				.stream().map(OnlinePaymentApproveDto::new).collect(Collectors.toList());
    		
    		if(!onlinePaymentApproveDto.isEmpty())
    		{
        		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "approvedAt"));
        		int start = (int) pageRequest.getOffset();
        		int end = Math.min(start + pageRequest.getPageSize(), onlinePaymentApproveDto.size());
        		Page<OnlinePaymentApproveDto> onlinePaymentpaging = new PageImpl<>(onlinePaymentApproveDto.subList(start, end), pageRequest, onlinePaymentApproveDto.size());
        		
        		//onlineApprove
    			model.addAttribute("onlinePaymentApprovepaging", onlinePaymentpaging);
    		}
    		else
    		{
    			model.addAttribute("onlinePaymentApprovepaging", "null");
    		}

    		
    		//오프라인 결제 내역
    		List<OfflinePaymentApproveDto1> offlinePaymentApproveDto = userService.userOfflineApproveFindAll(id)
    				.stream().map(OfflinePaymentApproveDto1::new).collect(Collectors.toList());
    		
    		if(!offlinePaymentApproveDto.isEmpty())
    		{
        		PageRequest pageRequest2 = PageRequest.of(page2, 5, Sort.by(Sort.Direction.DESC, "approvedAt"));
        		int start2 = (int) pageRequest2.getOffset();
        		int end2 = Math.min(start2 + pageRequest2.getPageSize(), offlinePaymentApproveDto.size());
        		Page<OfflinePaymentApproveDto1> offlinePaymentpaging = new PageImpl<>(offlinePaymentApproveDto.subList(start2, end2), pageRequest2, offlinePaymentApproveDto.size());
        		
        		//offlineApprove
    			model.addAttribute("offlinePaymentApprovepaging", offlinePaymentpaging);
    		}
    		else
    		{
    			model.addAttribute("offlinePaymentApprovepaging", "null");
    		}

    		
    		//온라인 결제취소 내역
    		List<OnlinePaymentCancelDto> onlinePaymentCancelDto = userService.userOnlineCancelFindAll(id)
    				.stream().map(OnlinePaymentCancelDto::new).collect(Collectors.toList());
			
    		if(!onlinePaymentCancelDto.isEmpty())
    		{
        		PageRequest pageRequest3 = PageRequest.of(page3, 5, Sort.by(Sort.Direction.DESC, "cancelAt"));
        		int start3 = (int) pageRequest3.getOffset();
        		int end3 = Math.min(start3 + pageRequest3.getPageSize(), onlinePaymentCancelDto.size());
        		Page<OnlinePaymentCancelDto> onlinePaymentCancelpaging = new PageImpl<>(onlinePaymentCancelDto.subList(start3, end3), pageRequest3, onlinePaymentCancelDto.size());
        		
        		//onlineCancel
    			model.addAttribute("onlinePaymentCancelpaging", onlinePaymentCancelpaging);
    		}
    		else
    		{
    			model.addAttribute("onlinePaymentCancelpaging", "null");
    		}
			
    		//오프라인 결제취소 내역
    		List<OfflinePaymentCancelDto> offlinePaymentCancelDto = userService.userOfflineCancelFindAll(id)
    				.stream().map(OfflinePaymentCancelDto::new).collect(Collectors.toList());

    		if(!offlinePaymentCancelDto.isEmpty())
    		{
        		PageRequest pageRequest4 = PageRequest.of(page4, 5, Sort.by(Sort.Direction.DESC, "cancelAt"));
        		int start4 = (int) pageRequest4.getOffset();
        		int end4 = Math.min(start4 + pageRequest4.getPageSize(), offlinePaymentCancelDto.size());
        		Page<OfflinePaymentCancelDto> offlinePaymentCancelpaging = new PageImpl<>(offlinePaymentCancelDto.subList(start4, end4), pageRequest4, offlinePaymentCancelDto.size());
        		
        		//offlineCancel
        		model.addAttribute("offlinePaymentCancelpaging", offlinePaymentCancelpaging);
    		}
    		else
    		{
    			model.addAttribute("offlinePaymentCancelpaging", "null");
    		}
    		
    		model.addAttribute("localDateTime", LocalDateTime.now());
    	}
    	
    	return "mypage/mypagePayment";
    }
    
    @PostMapping("/mypage/payment/onlinecancel")
    @ResponseBody
    public ResponseEntity<Object>mypageUserPaymentOnlineCancel(@RequestBody KakaoPartnerOrderIdDto kakaoPartnerOrderIdDto)
    {    	
    	KakaoPaymentCancelDto kakaoPaymentCancelDto = userService.userOnlineApproveFind(kakaoPartnerOrderIdDto.getPartnerOrderId());
    	
    	if(!kakaoPaymentCancelDto.getStatus().equals(Status.N))
    	{
    		return ResponseEntity.ok(400);
    	}
    	   	
    	KakaoPayCancel kakaoPayCancel = kakaoService.kakaoPayCancel(kakaoPaymentCancelDto);
    	
    	if(kakaoPayCancel != null)
    	{
    		OnlinePaymentCancelDto onlinePaymentCancelDto = new OnlinePaymentCancelDto();
    		onlinePaymentCancelDto = userService.userOnlineCancelInsert(onlinePaymentCancelDto.onlineResult(kakaoPayCancel, kakaoPaymentCancelDto));
    		return ResponseEntity.ok(200);
    	}
    	
    	return ResponseEntity.ok(400);
    }
    
    @PostMapping("/mypage/payment/offlinecancel")
    @ResponseBody
    public ResponseEntity<Object>mypageUserPaymentOfflineCancel(@RequestBody KakaoPartnerOrderIdDto kakaoPartnerOrderIdDto)
    {
    	KakaoPaymentCancelDto kakaoPaymentCancelDto = userService.userOfflineApproveFind(kakaoPartnerOrderIdDto.getPartnerOrderId());
    	
    	if(!kakaoPaymentCancelDto.getStatus().equals(Status.Y))
    	{
    		return ResponseEntity.ok(400);
    	}
    	
    	KakaoPayCancel kakaoPayCancel = kakaoService.kakaoPayCancel(kakaoPaymentCancelDto);
    	
    	if(kakaoPayCancel != null)
    	{
    		OfflinePaymentCancelDto offlinePaymentCancelDto = new OfflinePaymentCancelDto();
    		offlinePaymentCancelDto = userService.userOfflineCancelInsert(offlinePaymentCancelDto.offlineResult(kakaoPayCancel, kakaoPaymentCancelDto));
    		return ResponseEntity.ok(200);
    	}
    	
    	return ResponseEntity.ok(400);
    }
    
    
//===========================================================================================
    
    @GetMapping("/mypage/{id}/onlinelecturelist")
    public String mypageUserOnlineLecture(Model model, @PathVariable String id, @PageableDefault(size = 5) Pageable pageable)
    {
    	if(id != null)
    	{
    		Page<OnlinePaymentClassListDTO> classes = reviewService.userOnlineLectureList(id, pageable.getPageNumber());
    		
    		LocalDateTime now = LocalDateTime.now();
    		
    		//후기 미작성 및 결제상태 = Y(수강중) or R(재결제)인 경우 후기 작성 가능
    		Map<String, String> map = new HashMap<>();
			for (OnlinePaymentClassListDTO reviewChk : classes) {
				if(reviewChk.getStatus() == Status.N){
					map.put(String.valueOf(reviewChk.getPartnerOrderId()), "null");
				}else if(reviewService.exsitsReview(reviewChk.getPartnerOrderId()) == 0 && (reviewChk.getStatus() == Status.Y || reviewChk.getStatus() == Status.R)) {
					map.put(String.valueOf(reviewChk.getPartnerOrderId()), "write");
				}else if(reviewService.exsitsReview(reviewChk.getPartnerOrderId()) != 0) {
					map.put(String.valueOf(reviewChk.getPartnerOrderId()), "view");
				}
			}
        	model.addAttribute("classes", classes);
        	model.addAttribute("now", now);
        	model.addAttribute("reviewChk", map);
        	model.addAttribute("id", id);
    	}
    	
    	return "mypage/mypageOnlineLectureList";
    }
    
    @GetMapping("/mypage/{id}/offlinelecturelist")
    public String mypageUserOfflineLecture(Model model, @PathVariable String id, @PageableDefault(size = 5) Pageable pageable)
    {
    	if(id != null)
    	{
    		Page<OfflineClassPaymentListDTO> classes = reviewService.userOfflineLectureList(id, pageable.getPageNumber());
    		LocalDateTime now = LocalDateTime.now();
    		
    		Map<String, String> map = new HashMap<>();
			for (OfflineClassPaymentListDTO reviewChk : classes) {
				map.put(String.valueOf(reviewChk.getPartnerOrderId()),
						String.valueOf(reviewService.exsitsReview(reviewChk.getPartnerOrderId())));
			}
    		
    		model.addAttribute("classes", classes);
    		model.addAttribute("now", now);
    		model.addAttribute("reviewChk", map);
    		model.addAttribute("id", id);
    	}
    	
    	return "mypage/mypageOfflineLectureList";
    }
    
    
    
    @GetMapping("/mypage/{id}/onlinelikelist")
    public String mypageUserOnlineLike(Model model, @PathVariable String id, @RequestParam(value="page", defaultValue="0") int page)
    {
    	if(id != null)
    	{	//classLikeOnline
    		List<ClassLikeDTO> classLikeDto = userService.classLike(id, "on")
    				.stream().map(ClassLikeDTO :: new).collect(Collectors.toList());
    		
    			System.out.println(classLikeDto.isEmpty());
    		
    		
    		if(!classLikeDto.isEmpty())
    		{
        		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "classId"));
        		int start = (int) pageRequest.getOffset();
        		int end = Math.min(start + pageRequest.getPageSize(), classLikeDto.size());
        		Page<ClassLikeDTO> paging = new PageImpl<>(classLikeDto.subList(start, end), pageRequest, classLikeDto.size());
        		
        		
        		userService.onlineClassTitle(classLikeDto);
    			model.addAttribute("paging", paging);
    		}
    		else
    		{
    			model.addAttribute("paging", "null");
    		}
    	}
    	return "mypage/mypageOnlineLikeList";
    }
    
    
//===========================================================================================
    
    @PostMapping("/mypage/online/likecancel")
    @ResponseBody
    public ResponseEntity<Object> onlineLikeCancel(@RequestBody ClassLikeDTO classLikeDto)
    {
    	classLikeDto.setClassIden("on");
    	return ResponseEntity.ok(userService.onlineClassLikeDelete(classLikeDto));
    }
    
    @GetMapping("/mypage/{id}/offlinelikelist")
    public String mypageUserOfflineLike(Model model, @PathVariable String id, @RequestParam(value="page", defaultValue="0") int page)
    {
    	if(id != null)
    	{
    		//classLikeOffline
    		List<ClassLikeDTO> classLikeDto = userService.classLike(id, "OFF")
    				.stream().map(ClassLikeDTO :: new).collect(Collectors.toList());
    		
    		if(!classLikeDto.isEmpty())
    		{
        		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "classId"));
        		int start = (int) pageRequest.getOffset();
        		int end = Math.min(start + pageRequest.getPageSize(), classLikeDto.size());
        		Page<ClassLikeDTO> paging = new PageImpl<>(classLikeDto.subList(start, end), pageRequest, classLikeDto.size());
        		
    			userService.offlineClassTitle(classLikeDto);
    			model.addAttribute("paging", paging);
    		}
    		else
    		{
    			model.addAttribute("paging", "null");
    		}
    	}
    	
    	return "mypage/mypageOfflineLikeList";
    }
    
    @PostMapping("/mypage/offline/likecancel")
    @ResponseBody
    public ResponseEntity<Object> offlineLikeCancel(@RequestBody ClassLikeDTO classLikeDto)
    {
    	classLikeDto.setClassIden("OFF");
    	return ResponseEntity.ok(userService.offlineClassLikeDelete(classLikeDto));
    }
    
    @GetMapping("/mypage/{id}/reference")
    public String mypageUserReference(Model model, @PathVariable String id, @RequestParam(value="page", defaultValue="0") int page)
    {
    	if(id != null)
    	{
    		List<ReferenceDto2> userReferenceDto = userService.userReferenceList(id)
    				.stream().map(ReferenceDto2::new).collect(Collectors.toList());
    		
    		if(!userReferenceDto.isEmpty())
    		{
        		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "refId"));
        		int start = (int) pageRequest.getOffset();
        		int end = Math.min(start + pageRequest.getPageSize(), userReferenceDto.size());
        		Page<ReferenceDto2> paging = new PageImpl<>(userReferenceDto.subList(start, end), pageRequest, userReferenceDto.size());
        		
    			userService.referenceAnswerList(userReferenceDto);
    			model.addAttribute("paging", paging);
    		}
    		else
    		{
    			model.addAttribute("paging", "null");
    		}
    	}
    	
    	return "mypage/mypageReference";
    }
}