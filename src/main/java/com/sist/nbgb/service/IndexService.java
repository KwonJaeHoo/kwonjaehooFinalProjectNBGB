package com.sist.nbgb.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewFile;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.ReviewFileRepository;
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
	private final ReviewFileRepository reviewFileRepository;
	
	private final UserService userService;
	
	public List<OnlineClass> indexOnlineClassView()
	{
		return onlineClassRepository.findAll(Sort.by(Sort.Direction.DESC, "onlineClassViews"));
	}
	
	public List<OfflineClass> indexOfflineClassView()
	{
		return offlineRepository.findAll(Sort.by(Sort.Direction.DESC, "offlineClassViews"));
	}
	
	public List<Review> reviewDTO()
	{
		return reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "reviewRating"));
	}
	
	public List<User> userDto()
	{
		return userRepository.findAll();
	}
	
	public List<ReviewFile> reviewFileDto()
	{
		return reviewFileRepository.findAll();
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
}
