package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.OfflinePostDto;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.OfflineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineService
{
	private final OfflineRepository offlineRepository;
	
	public List<OfflineClass> findAll()
	{	
		return offlineRepository.findAll();
	}
	
	public List<OfflineClass> findByUpload(Status approve)
	{
		return offlineRepository.findByUpload(approve);
	}
	
	public List<OfflineClass> findByCategory(String search, Status approve)
	{
		return offlineRepository.findByCategory(search, approve);
	}
	
	public List<OfflineClass> findByPalce(String search, Status approve)
	{
		return offlineRepository.findByPalce(search, approve);
	}
	
	public List<OfflineClass> findByTwoPlace(String search1, String search2, Status approve)
	{
		return offlineRepository.findByTwoPlace(search1, search2, approve);
	}
	
	public List<OfflineClass> findBySearch(String search, Status offlineClassApprove)
	{
		return offlineRepository.findBySearch(search, offlineClassApprove);
	}
	
	//중복검색
	public List<OfflineClass> findCateKeyword(String search1, String search2, Status offlineClassApprove)
	{
		return offlineRepository.findCateKeyword(search1, search2, offlineClassApprove);
	}
	
	public List<OfflineClass> findPlaceKeyword(String search1, String search2, Status offlineClassApprove)
	{
		return offlineRepository.findPlaceKeyword(search1, search2, offlineClassApprove);
	}
	
	public List<OfflineClass> findTwoPlaceKeyword(String search1, String search2, String search3, Status offlineClassApprove)
	{
		return offlineRepository.findTwoPlaceKeyword(search1, search2, search3, offlineClassApprove);
	}
	
	
	//뷰 페이지
	public OfflineClass findByView(Long offlineClassId)
	{
		return offlineRepository.findByView(offlineClassId);
	}
	
	public int updateViews(Long offlineClassId)
	{
		return offlineRepository.updateViews(offlineClassId);
	}
	
	//오프라인 등록
	@Transactional
	public OfflinePostDto offlinePost(OfflinePostDto offlinePostDto)
	{
		Instructors id = findByView((long) 5).getInstructorId();
		
		OfflineClass offlineClass = OfflineClass.builder()
				.offlineClassTitle(offlinePostDto.getOfflineClassTitle())
				.offlineClassContent(offlinePostDto.getOfflineClassContent())
				.offlineClassRegdate(LocalDateTime.now())
				.offlineClassPlace(offlinePostDto.getOfflineClassPlace())
				.offlineClassCategory(offlinePostDto.getOfflineClassCategory())
				.instructorId(id)
				.offlineClassPrice(offlinePostDto.getOfflineClassPrice())
				.offlineClassApprove(Status.Y)
				.rejection(null)
				.offlineClassLimitPeople(offlinePostDto.getOfflineClassLimitPeople())
				.offlineClassViews((long) 0)
				.build();
		
		System.out.println("333333333333333333333333333");
		
		 // 저장 후에 offlineClassId 값을 가져옵니다.
	    OfflineClass savedOfflineClass = offlineRepository.save(offlineClass);
	    Long offlineClassId = savedOfflineClass.getOfflineClassId();
	    
	    System.out.println("4444444444444444444444444444");

	    // 반환할 DTO에 offlineClassId 값을 설정한 후에 반환합니다.
	    offlinePostDto.setOfflineClassId(offlineClassId);
	    
	    System.out.println("5555555555555555555555555555");
	    
	    return offlinePostDto;
	}
}
