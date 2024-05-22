package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sist.nbgb.dto.AttributeDto;
import com.sist.nbgb.dto.ReferenceDto2;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;
import com.sist.nbgb.entity.ReferenceAnswer;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.AdminRepository;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassFileRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.ReferenceAnswerRepository;
import com.sist.nbgb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService 
{
	private final UserRepository userRepository;
	private final InstructorsRepository instructorsRepository;
	private final AdminRepository adminRepository;
	private final OnlineClassRepository onlineClassRepository;
	private final OfflineRepository offlineRepository;
	private final ReferenceAnswerRepository referenceAnswerRepository;
	private final OnlineClassFileRepository onlineClassFileRepository;
	
	
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
	public Page<OnlineClass> findAllByApproveStatusAndOrderByRegdateDesc(Pageable pageable) {
		return onlineClassRepository.findAllByApproveStatusAndOrderByRegdateDesc(pageable);
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
	
	//온라인 클래스 리스트
	public OnlineClass findById(Long onlineClassId) {
		return onlineClassRepository.findByOnlineClassId(onlineClassId);
	}
	
	public void denyOnlineClass(Long onlineClassId, String rejection) {
        OnlineClass onlineClass = onlineClassRepository.findById(onlineClassId)
            .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다. id=" + onlineClassId));
        onlineClass.setRejection(rejection);
        onlineClass.setOnlineClassApprove(Status.S);
        onlineClass.setRejectionRegdate(LocalDateTime.now());
        onlineClassRepository.save(onlineClass);
    }
	
	//온라인 클래스 강의 자료 다운로드 페이지
	public List<OnlineClassFile> findOnlineClassFilesByClassId(OnlineClass onlineClassId) {
	    return onlineClassFileRepository.findByOnlineClassId(onlineClassId);
	}
	
	//온라인 클래스 강의 자료 다운로드
	public OnlineClassFile findOnlineClassFileById(Long onlineClassId, Long onlineFileId) {
	    return onlineClassFileRepository.findByOnlineClassFileIdOnlineClassIdAndOnlineClassFileIdOnlineFileId(onlineClassId, onlineFileId);
	}
	
	
	//페이징 처리(오프라인 강의 리스트)
	public Page<OfflineClass> findOfflineByApproveStatusAndOrderByRegdateDesc(Pageable pageable) {
		return offlineRepository.findAllByApproveStatusAndOrderByRegdateDesc(pageable);
	}
	
	//검색 기능(오프라인 강의 리스트)
	public Page<OfflineClass> findOfflineClassByKeyword(String keyword, Pageable pageable) {
		return offlineRepository.findByOfflineClassTitleContainingOrOfflineClassContentContaining(keyword, keyword, pageable);
	}
	
	//오프라인 미승인 강의 승인하기
	public void changeOfflineToApprove(Long offlineClassId, Status newStatus) throws Exception {
		OfflineClass offlineClass = offlineRepository.findAllByOfflineClassId(offlineClassId);
		offlineClass.setOfflineClassApprove(newStatus);
		offlineRepository.save(offlineClass);
	}
	
	//오프라인 클래스 리스트
	public OfflineClass findAllByOfflineClassId(Long offlineClassId) {
		return offlineRepository.findAllByOfflineClassId(offlineClassId);
	}
	
	public void denyOfflineClass(Long offlineClassId, String rejection) {
        OfflineClass offlineClass = offlineRepository.findById(offlineClassId)
            .orElse(null);
        offlineClass.setRejection(rejection);
        offlineClass.setOfflineClassApprove(Status.S);
        offlineClass.setRejectionRegdate(LocalDateTime.now());
        offlineRepository.save(offlineClass);
    }
	
	//문의 답변
	public void referenceAnswerFind(List<ReferenceDto2> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).setReferenceAnswer(referenceAnswerRepository.findByRefId(list.get(i).getRefId().getRefId()));
		}
	}
	
	//문의 답변 달기
	public void refAnswer(Long refId, String refAnswerContent) {
	    ReferenceAnswer referenceAnswer = referenceAnswerRepository.findByRefId(refId);
	    if (referenceAnswer == null) {
	        referenceAnswer = new ReferenceAnswer();
	        referenceAnswer.setRefId(refId);
	    }
	    referenceAnswer.setRefAnswerContent(refAnswerContent);
	    referenceAnswerRepository.save(referenceAnswer);
	}
	
	public AttributeDto findAdminAttribute(String adminId)
	{
		return AttributeDto.adminAttribute(adminRepository.findAdminNameByAdminId(adminId));
	}
}
