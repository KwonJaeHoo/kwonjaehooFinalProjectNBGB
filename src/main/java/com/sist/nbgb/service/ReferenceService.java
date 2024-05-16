package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.ReferenceDTO;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.repository.ReferenceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReferenceService {
	
	private final ReferenceRepository referenceRepository;
	
	//상세 페이지(View)
	public List<Reference> findByView(Long refId)
	{
		return referenceRepository.findByView(refId);
	}
	
	//문의글 등록
	@Transactional
	public ReferenceDTO referenceDTO(ReferenceDTO refDTO)
	{	
		Reference reference = Reference.builder()
				.refId(refDTO.getRefId())
				.refTitle(refDTO.getRefTitle())
				.refContent(refDTO.getRefContent())
				.userId(refDTO.getUserId())
				.refRegdate(LocalDateTime.now())
				.build();
		
		System.out.println("11111111111111111111111111111111111111111111");
		
		//Reference savedReference = ReferenceRepository
		
		
		return refDTO;		
	}
	
	
	//페이징 처리
	public List<Reference> findByrefRegdate() 
	{	
		return referenceRepository.findAllByOrderByRefRegdateDesc();
	}
	
	//검색 기능
	public List<Reference> findByKeyword(String keyword) {
	    return referenceRepository.findByRefTitleContainingOrRefContentContainingOrUserId_UserIdContaining(keyword, keyword, keyword);
	}

    public void saveReference(ReferenceDTO refDTO) {
        Reference reference = Reference.builder()
                .refTitle(refDTO.getRefTitle())
                .refContent(refDTO.getRefContent())
                .userId(refDTO.getUserId()) 
                .refRegdate(LocalDateTime.now())
                .build();
        referenceRepository.save(reference);
    }

}
