package com.sist.nbgb.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorInfoDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.repository.InstructorsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorsService 
{
	private final InstructorsRepository instructorsRepository;
	private final PasswordEncoder passwordEncoder;
	
	public InstructorIdCheckDto findByInstructorId(InstructorIdCheckDto instructorIdCheckDto)
	{
		return instructorsRepository.findByInstructorId(instructorIdCheckDto.getInstructorId())
				.map(InstructorIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
	
	
	public InstructorInfoDto findByInstructorId(String userId)
	{
		return InstructorInfoDto.infoInstructor(instructorsRepository.findAllByInstructorId(userId)); 
	}
	
	
	@Transactional
	public Object changeInstructorPassword(String instructorId, String instructorPassword)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorPassword(passwordEncoder.encode(instructorPassword)));	
			
		return 200;
	}
	
	@Transactional
	public Object changeInstructorNickname(String instructorId, String instructorNickname)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorNickname(instructorNickname));	
			
		return 200;
	}
	
	@Transactional
	public Object changeInstructorEmail(String instructorId, String instructorEmail)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorEmail(instructorEmail));	
			
		return 200;
	}
	
	@Transactional
	public Object changeInstructorPhone(String instructorId, String instructorPhone)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorPhone(instructorPhone));	
			
		return 200;
	}
	
	@Transactional
	public Object changeInstructorBankAccount(String instructorId, String instructorBank, String instructorAccount)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorBank(instructorBank));
		instructors.ifPresent(value -> value.setInstructorAccount(instructorAccount));
			
		return 200;
	}
	
	@Transactional
	public Object changeInstructorCategory(String instructorId, String instructorCategory)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		instructors.ifPresent(value -> value.setInstructorCategory(instructorCategory));
			
		return 200;
	}
	
	@Transactional
	public Object changeUserFile()
	{
		return 200;
	}
}
