package com.sist.nbgb.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.EmailCheckDto;
import com.sist.nbgb.dto.FindIdDto;
import com.sist.nbgb.dto.FindPasswordDto;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.UserIdCheckDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindService 
{
	private final UserRepository userRepository;
	private final InstructorsRepository instructorsRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	
	public UserIdCheckDto findUserId(FindIdDto findIdDto)
	{		
		return UserIdCheckDto.of(userRepository.findUserIdByUserNameAndUserEmailAndUserProvider(findIdDto.getName(), findIdDto.getEmail(), Provider.LOCAL));
	}	
	
	public InstructorIdCheckDto findInstructorId(FindIdDto findIdDto)
	{
		return InstructorIdCheckDto.of(instructorsRepository.findInstructorIdByInstructorNameAndInstructorEmail(findIdDto.getName(), findIdDto.getEmail()));
	}
	
	public EmailCheckDto findUserPassword(FindPasswordDto findPasswordDto)
	{
		return EmailCheckDto.findUser(userRepository.findUserEmailByUserIdAndUserNameAndUserEmailAndUserProvider(findPasswordDto.getId(), findPasswordDto.getName(), findPasswordDto.getEmail(), Provider.LOCAL));
	}
	
	public EmailCheckDto findInstructorPassword(FindPasswordDto findPasswordDto)
	{
		return EmailCheckDto.findInstructor(instructorsRepository.findInstructorEmailByInstructorIdAndInstructorNameAndInstructorEmail(findPasswordDto.getId(), findPasswordDto.getName(), findPasswordDto.getEmail()));
	}
	
	@Transactional
	public String userChangePassword(FindPasswordDto findPasswordDto) throws MessagingException, UnsupportedEncodingException 
	{
		String changePassword = emailService.sendEmailPassword(findPasswordDto.getEmail());
		
		if(changePassword != null)
		{
			Optional<User> user = userRepository.findByUserId(findPasswordDto.getId());
			user.ifPresent(value -> value.setUserPassword(passwordEncoder.encode(changePassword)));
		}
		else
		{
			throw new UnsupportedEncodingException("오류가 발생했습니다.");
		}
		
		return changePassword;
	}
	
	@Transactional
	public String instructorChangePassword(FindPasswordDto findPasswordDto) throws MessagingException, UnsupportedEncodingException 
	{
		String changePassword = emailService.sendEmailPassword(findPasswordDto.getEmail());
		
		if(changePassword != null)
		{
			Optional<Instructors> instructors = instructorsRepository.findByInstructorId(findPasswordDto.getId());
			instructors.ifPresent(value -> value.setInstructorPassword(passwordEncoder.encode(changePassword)));
		}
		else
		{
			throw new UnsupportedEncodingException("오류가 발생했습니다.");
		}
		
		return changePassword;
	}
}