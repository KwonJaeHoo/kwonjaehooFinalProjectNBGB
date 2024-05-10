package com.sist.nbgb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflinePaymentApproveRepository;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OfflineUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineInstructorService 
{
	private final OfflineRepository offlineRepository;
	private final InstructorsRepository instrucRepostiory;
	private final OfflinePaymentApproveRepository offPayRepository;
	private final OfflineUserRepository userRepository;
	
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
	
	//마이페이지 페이징 - 결제
	public List<OfflineClass> offlinePayInstructor(int page, String id, Status onlineClassApprove)
	{
//		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OfflineClassId"));
		
//		Instructors instructorId = instrucRepostiory.findFirstByInstructorId(id);
		
		return offlineRepository.findInsList(id);
	}
	
	//시간 리스트 가져오기
	public ArrayList<String> timeList(String offlineClassId, String date)
	{
		return offPayRepository.timeList(offlineClassId, date);
	}
	
	//예약한 사람 가져오기
	public List<User> payList(String date, String time, String id)
	{
		return userRepository.findList(id, date, time);
	}
}
