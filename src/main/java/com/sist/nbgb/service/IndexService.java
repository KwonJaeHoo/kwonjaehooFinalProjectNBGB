package com.sist.nbgb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.Review;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OnlineClassRepository;
import com.sist.nbgb.repository.OnlineReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexService 
{
	private final OnlineClassRepository onlineClassRepository;
	private final OfflineRepository offlineRepository;
	private final OnlineReviewRepository reviewRepository;
	
	public List<OnlineClass> indexOnlineClassView()
	{
		return onlineClassRepository.findAll();
	}
	
	public List<OfflineClass> indexOfflineClassView()
	{
		return offlineRepository.findAll();
	}
	
	public List<Review> reviewDTO()
	{
		return reviewRepository.findAll();
	}
}
