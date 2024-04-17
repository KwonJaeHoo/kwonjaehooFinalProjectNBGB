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
	
	public List<OfflineClass> findByofflineClassCategoryContaining(String search)
	{
		return offlineRepository.findByOfflineClassCategoryContaining(search);
	}
	
	public List<OfflineClass> findByOfflineClassPlaceContaining(String search)
	{
		return offlineRepository.findByOfflineClassPlaceContaining(search);
	}
	
	public List<OfflineClass> findByOfflineClassPlaceOrOfflineClassPlaceContaining(String search1, String search2)
	{
		return offlineRepository.findByOfflineClassPlaceContainingOrOfflineClassPlaceContaining(search1, search2);
	}
}
