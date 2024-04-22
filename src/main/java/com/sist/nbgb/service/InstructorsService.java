package com.sist.nbgb.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorsDto;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorsService 
{
	private final InstructorsRepository instructorsRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Boolean instructorSignupDuplicateId(InstructorIdCheckDto instructorIdCheckDto)
	{
		return instructorsRepository.existsByInstructorId(instructorIdCheckDto.getInstructorId());
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
