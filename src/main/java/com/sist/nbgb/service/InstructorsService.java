package com.sist.nbgb.service;

import org.springframework.stereotype.Service;
import com.sist.nbgb.dto.InstructorIdCheckDto;
import com.sist.nbgb.repository.InstructorsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorsService 
{
	private final InstructorsRepository instructorsRepository;
	
	public InstructorIdCheckDto findByInstructorId(InstructorIdCheckDto instructorIdCheckDto)
	{
		return instructorsRepository.findByInstructorId(instructorIdCheckDto.getInstructorId())
				.map(InstructorIdCheckDto :: of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
}
