package com.sist.nbgb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService 
{
	private final UserRepository userRepository;
	private final InstructorsRepository instructorsRepository;
	private final OnlineClassRepository onlineClassRepository;
	private final OfflineRepository offlineRepository;
	
	//페이징 처리(일반 회원)
	public Page<User> findByUserRegdate(Pageable pageable) {
		return userRepository.findAllByOrderByUserRegdateDesc(pageable);
	}
	
	//검색 기능(일반 회원)
	public Page<User> findUserByKeyword(String keyword, Pageable pageable) {
		return userRepository.findByUserIdContainingOrUserEmailContainingOrUserNameContainingOrUserPhoneContaining(keyword, keyword, keyword, keyword, pageable);
	}
	
	//일반 회원 권한 변경
	public void changeUserRole(String userId, Role newRole) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
		user.setAuthority(newRole);
		userRepository.save(user);
	}
	
	
	
	//페이징 처리(강사)
	public Page<Instructors> findAllByOrderByInstructorRegdate(Pageable pageable) {
		return instructorsRepository.findAllByOrderByInstructorRegdateDesc(pageable);
	}
	
	//검색 기능(강사)
	public Page<Instructors> findInsturctorByKeyword(String keyword, Pageable pageable) {
		return instructorsRepository.findByInstructorIdContainingOrInstructorEmailContainingOrInstructorNicknameContainingOrInstructorPhoneContaining(keyword, keyword, keyword, keyword, pageable);
	}
	
	//페이징 처리(온라인 강의 리스트)
	public Page<OnlineClass> findByOnlineClassRegdate(Pageable pageable) {
		return onlineClassRepository.findAllByOrderByOnlineClassRegdateDesc(pageable);
	}
	
	//검색 기능(온라인 강의 리스트)
	public Page<OnlineClass> findOnlineClassByKeyword(String keyword, Pageable pageable) {
		return onlineClassRepository.findByOnlineClassTitleContainingOrOnlineClassContentContaining(keyword, keyword, pageable);
	}
	
	//온라인 미승인 강의 승인하기
	public void changeOnlineToApprove(Long onlineClassId, Status newStatus) throws Exception {
		OnlineClass onlineClass = onlineClassRepository.findByOnlineClassId(onlineClassId);
		onlineClass.setOnlineClassApprove(newStatus);
		onlineClassRepository.save(onlineClass);
	}
	
	
	
	
	//페이징 처리(오프라인 강의 리스트)
	public Page<OfflineClass> findByOfflineClassRegdate(Pageable pageable) {
		return offlineRepository.findAllByOrderByOfflineClassRegdateDesc(pageable);
	}
	
	//검색 기능(오프라인 강의 리스트)
	public Page<OfflineClass> findOfflineClassByKeyword(String keyword, Pageable pageable) {
		return offlineRepository.findByOfflineClassTitleContainingOrOfflineClassContentContaining(keyword, keyword, pageable);
	}
}
