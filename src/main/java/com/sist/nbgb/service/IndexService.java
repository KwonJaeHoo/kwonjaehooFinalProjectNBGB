package com.sist.nbgb.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.ReviewRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexService 
{
	private final OnlineClassRepository onlineClassRepository;
	private final OfflineRepository offlineRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	
	private final UserService userService;
	
	public List<OnlineClass> indexOnlineClassView()
	{
		return onlineClassRepository.findAllByOnlineClassApproveOrderByOnlineClassViewsDesc(Status.Y);
	}
	

	public List<OfflineClass> indexOfflineClassView()
	{
		return offlineRepository.findAllByOfflineClassApproveOrderByOfflineClassViewsDesc(Status.Y);
	}
	
	public List<Review> reviewDTO()
	{
		return reviewRepository.findAllByReviewStatusOrderByReviewRatingDesc(Status.Y);
	}
	
	public List<User> userDto()
	{
		return userRepository.findAll();
	}
	

	
	//회원 이미지 찾기
	public String getImg(String userId) {
		String img = "N";
		UserInfoDto userInfoDto = userService.findByUserId(userId);
    	
		String path = "C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\images\\user";
	    String filename = userInfoDto.getUserId() + ".png"; // 기본 파일명
	    String filepath = path + "/" + filename;
		
        File file = new File(filepath);
        
        if(file.exists())
		{
        	img = "Y";
		}
        
        return img;
	}
	
	public String findOnTitle(Long onlineClassId) {
		OnlineClass on = onlineClassRepository.findByOnlineClassId(onlineClassId);
		return on.getOnlineClassTitle();
	}
	
	public String findOffTitle(Long offlineClassId) {
		Optional<OfflineClass> off = offlineRepository.findByOfflineClassId(offlineClassId);
		return off.get().getOfflineClassTitle();
	}
	
	public OnlineClass findOnline(Long onlineClassId) {
		return onlineClassRepository.findByOnlineClassId(onlineClassId);
	}
	
	public OfflineClass findOffline(Long offlineClassId) {
		return offlineRepository.findAllByOfflineClassId(offlineClassId);
	}
	
}
