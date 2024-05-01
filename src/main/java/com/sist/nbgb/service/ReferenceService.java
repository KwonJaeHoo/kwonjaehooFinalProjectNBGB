package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.ReferenceDTO;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.repository.ReferenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReferenceService {
	
	private final ReferenceRepository referenceRepository;
	
	//문의글 최신순 조회
	public List<Reference> findByrefRegdate() {
		
		return referenceRepository.findAllByOrderByRefRegdateDesc();
	}
	
	//문의글 등록
	@Transactional
	public ReferenceDTO referenceDTO(ReferenceDTO refDTO)
	{	
		Reference reference = Reference.builder()
				.refId(refDTO.getRefId())
				.refTitle(refDTO.getRefTitle())
				.refContent(refDTO.getRefContent())
				.refRegdate(LocalDateTime.now())
				.UserId(refDTO.getUserId())
				.build();
		
		System.out.println("11111111111111111111111111111111111111111111");
		
		//Reference savedReference = ReferenceRepository
		
		
		return refDTO;		
	}

}
