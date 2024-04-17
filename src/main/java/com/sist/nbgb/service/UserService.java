package com.sist.nbgb.service;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserDto SignUp(@Valid UserDto userDto)
	{
	
		User user = User.builder()
				.userId(userDto.getUserId())
				.userPassword(passwordEncoder.encode(userDto.getUserPassword()))
				.userName(userDto.getUserName())
				.userNickname(userDto.getUserNickname())
				.userEmail(userDto.getUserEmail())
				.userPhone(userDto.getUserPhone())
				.userBirth(userDto.getUserBirth())
				.userGender(userDto.getUserGender())
				.userPoint((long)0)
				.userProvider(Provider.LOCAL)
				.Authority(Role.ROLE_USER)
				.userStatus(Status.Y)
				.userRegdate(LocalDateTime.now())
				.build();
		return UserDto.from(userRepository.save(user));
	}
}
