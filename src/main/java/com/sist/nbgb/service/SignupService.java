package com.sist.nbgb.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorsDto;
import com.sist.nbgb.dto.UserDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService 
{
	private final UserRepository userRepository;
	private final InstructorsRepository instructorsRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Boolean userSignupDuplicateId(UserIdCheckDto userIdCheckDto)
	{
		return userRepository.existsByUserId(userIdCheckDto.getUserId());
	}
	
	public Boolean instructorSignupDuplicateId(InstructorIdCheckDto instructorIdCheckDto)
	{
		return instructorsRepository.existsByInstructorId(instructorIdCheckDto.getInstructorId());
	}
	
	@Transactional
	public UserDto userSignup(UserDto userDto) throws RuntimeException
	{
        if(userRepository.findById(userDto.getUserId()).orElse(null) != null) 
        {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
		
		User user = User.builder()
				.userId(userDto.getUserId())
				.userPassword(passwordEncoder.encode(userDto.getUserPassword()))
				.userName(userDto.getUserName())
				.userNickname(userDto.getUserNickname())
				.userEmail(userDto.getUserEmail())
				.userPhone(userDto.getUserPhone())
				.userBirth(userDto.getUserBirth().replaceAll("-", ""))
				.userGender(userDto.getUserGender())
				.userPoint((long)0)
				.userProvider(Provider.LOCAL)
				.Authority(Role.ROLE_USER)
				.userStatus(Status.Y)
				.userRegdate(LocalDateTime.now().withNano(0))
				.build();
		
		return UserDto.from(userRepository.save(user));
	}

	@Transactional
	public InstructorsDto instructorSignup(InstructorsDto instructorsDto)
	{
        if(instructorsRepository.findById(instructorsDto.getInstructorId()).orElse(null) != null) 
        {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
		
		Instructors instructors = Instructors.builder()
				.instructorId(instructorsDto.getInstructorId())
				.instructorPassword(passwordEncoder.encode(instructorsDto.getInstructorPassword()))
				.instructorName(instructorsDto.getInstructorName())
				.instructorNickname(instructorsDto.getInstructorNickname())
				.instructorEmail(instructorsDto.getInstructorEmail())
				.instructorPhone(instructorsDto.getInstructorPhone())
				.instructorBank(instructorsDto.getInstructorBank())
				.instructorAccount(instructorsDto.getInstructorAccount())
				.Authority(Role.ROLE_INSTRUCTOR)
				.instructorStatus(Status.Y)
				.instructorRegdate(LocalDateTime.now())
				.instructorCategory(instructorsDto.getInstructorCategory())
				.build();
		
		return InstructorsDto.from(instructorsRepository.save(instructors));
	}
}
