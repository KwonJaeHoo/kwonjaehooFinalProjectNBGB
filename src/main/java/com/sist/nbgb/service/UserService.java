package com.sist.nbgb.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.dto.UserInfoDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto1;
import com.sist.nbgb.dto.OnlinePaymentApproveDto;
import com.sist.nbgb.dto.UserFileDto;
import com.sist.nbgb.dto.UserReviewDto;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.OfflinePaymentCancel;
import com.sist.nbgb.entity.OnlinePaymentApprove;
import com.sist.nbgb.entity.OnlinePaymentCancel;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.entity.UserFile;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.repository.OfflinePaymentApproveRepository;
import com.sist.nbgb.repository.OfflinePaymentCancelRepository;
import com.sist.nbgb.repository.OnlinePaymentApproveRepository;
import com.sist.nbgb.repository.OnlinePaymentCancelRepository;
import com.sist.nbgb.repository.ReviewRepository;
import com.sist.nbgb.repository.UserFileRepository;
import com.sist.nbgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OnlinePaymentApproveRepository onlinePaymentApproveRepository;
	private final OfflinePaymentApproveRepository offlinePaymentApproveRepository;
	private final OnlinePaymentCancelRepository onlinePaymentCancelRepository;
	private final OfflinePaymentCancelRepository offlinePaymentCancelRepository;
	private final UserFileRepository userFileRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	public UserIdCheckDto findByUserId(UserIdCheckDto userIdCheckDto)
	{
		return userRepository.findByUserId(userIdCheckDto.getUserId())
				.map(UserIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
	
	public User findUserById(String userId)
	{
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
	
	public UserInfoDto findByUserId(String userId)
	{
		return UserInfoDto.infoUser(userRepository.findAllByUserId(userId));
	}
	
	public List<OnlinePaymentApprove> userOnlineApproveFindAll(String userId)
	{
		return onlinePaymentApproveRepository.findAllByPartnerUserId(userId);
	}
	
	public List<OfflinePaymentApprove> userOfflineApproveFindAll(String userId)
	{
		return offlinePaymentApproveRepository.findAllByPartnerUserId(userId);
	}
	
	public List<OnlinePaymentCancel> userOnlineCancelFindAll(String userId)
	{
		return onlinePaymentCancelRepository.findAllByPartnerUserId(userId);
	}
	
	public List<OfflinePaymentCancel> userOfflineCancelFindAll(String userId)
	{
		return offlinePaymentCancelRepository.findAllByPartnerUserId(userId);
	}
	
	public List<Review> userReviewFindAll(String userId)
	{
		User user = new User();
		user.setUserId(userId);
		return reviewRepository.findAllByUserId(user);
	}
	
	public UserFileDto findByUserIdFile(String userId)
	{
		User user = new User();
		user.setUserId(userId);
		
		List<UserFile> userFile = userFileRepository.findAllByUserId(user);
		
		if(userFile.isEmpty())
		{
			return null;
		}
		else
		{
			return UserFileDto.fileOut(userFile);				
		}
	}
	
	@Transactional
	public Object changeUserPassword(String userId, String userPassword)
	{
		Optional<User> user = userRepository.findByUserId(userId);
		
		user.ifPresent(value -> value.setUserPassword(passwordEncoder.encode(userPassword)));	
			
		return 200;
	}
	
	@Transactional
	public Object changeUserNickname(String userId, String userNickname)
	{
		Optional<User> user = userRepository.findByUserId(userId);
		
		user.ifPresent(value -> value.setUserNickname(userNickname));
		return 200;
	}
	
	@Transactional
	public Object changeUserEmail(String userId, String userEmail) 
	{
		Optional<User> user = userRepository.findByUserId(userId);
		
		user.ifPresent(value -> value.setUserEmail(userEmail));
		return 200;
	}
	
	
	@Transactional
	public Object changeUserPhone(String userId, String userPhone) 
	{
		Optional<User> user = userRepository.findByUserId(userId);
		
		user.ifPresent(value -> value.setUserPhone(userPhone));
		return 200;
	}
	
	@Transactional
	public Object uploadUserFile(UserFileDto userFileDto)
	{
		
		return 200;
	}
	
	
	@Transactional
	public Object changeUserFile(UserFileDto userFileDto)
	{
		return 200;
	}
	
	@Transactional
	public Object signoutUser(String userId)
	{
		Optional<User> user = userRepository.findByUserId(userId);
		user.ifPresent(value -> value.setAuthority(Role.ROLE_RESIGN));
		
		return 200;
	}
}
