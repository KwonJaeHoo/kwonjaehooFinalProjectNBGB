package com.sist.nbgb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineInstructorService 
{
	private final OfflineRepository offlineRepository;
	private final InstructorsRepository instrucRepostiory;
	
	//목록 불러오기
	public List<OfflineClass> findByInstructorIdDesc(String id)
	{
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findByInstructorIdOrderByOfflineClassIdDesc(instructorId);
	}
	
	//목록 페이징
	public Page<OfflineClass> findByInstructorId(int page, String id)
	{
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OfflineClassId"));
		
		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findByInstructorId(pageable, instructorId);
	}
}
