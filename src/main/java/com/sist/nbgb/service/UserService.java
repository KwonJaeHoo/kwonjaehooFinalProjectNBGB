package com.sist.nbgb.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final UserRepository userRepository;
	
	private final InstructorsRepository instructorsRepository;
	
	public UserIdCheckDto findByUserId(String userId)
	{
		return userRepository.findByUserId(userId)
				.map(UserIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
	
	public InstructorIdCheckDto findByInstructorId(InstructorIdCheckDto instructorIdCheckDto)
	{
		return instructorsRepository.findByInstructorId(instructorIdCheckDto.getInstructorId())
				.map(InstructorIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
}
