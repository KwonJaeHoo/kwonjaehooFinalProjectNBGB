package com.sist.nbgb.service;

import org.springframework.stereotype.Service;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final UserRepository userRepository;
	
	public UserIdCheckDto findByUserId(UserIdCheckDto userIdCheckDto)
	{
		return userRepository.findByUserId(userIdCheckDto.getUserId())
				.map(UserIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
}
