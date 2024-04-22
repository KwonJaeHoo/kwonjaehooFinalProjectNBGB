package com.sist.nbgb.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
