package com.sist.nbgb.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.dto.InstructorInfoDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorsService 
{
	private final InstructorsRepository instructorsRepository;
	private final OfflineRepository offlineRepository;
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
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorPassword(passwordEncoder.encode(instructorPassword)));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object changeInstructorNickname(String instructorId, String instructorNickname)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorNickname(instructorNickname));			
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object changeInstructorEmail(String instructorId, String instructorEmail)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorEmail(instructorEmail));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object changeInstructorPhone(String instructorId, String instructorPhone)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorPhone(instructorPhone));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object changeInstructorBankAccount(String instructorId, String instructorBank, String instructorAccount)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorBank(instructorBank));
			instructors.ifPresent(value -> value.setInstructorAccount(instructorAccount));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object changeInstructorCategory(String instructorId, String instructorCategory)
	{
		Optional<Instructors> instructors = instructorsRepository.findByInstructorId(instructorId);
		
		try 
		{
			instructors.ifPresent(value -> value.setInstructorCategory(instructorCategory));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
	
	@Transactional
	public Object instructorOfflineLectureStatusChage(Long offlineClassId)
	{
		Optional<OfflineClass> offlineClass = offlineRepository.findByOfflineClassId(offlineClassId);
		
		try 
		{
			offlineClass.ifPresent(value -> value.setOfflineClassApprove(Status.B));	
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return 200;
	}
}
