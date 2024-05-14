package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.ReferenceAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReferenceAnswerDto 
{
	public Long refId;
	
	public String adminId;
	
	public String refAnswerContent;
	
	public LocalDateTime refAnswerRegdate;
	
	
	public ReferenceAnswerDto referenceAnswer(ReferenceAnswer referenceAnswer)
	{
		return ReferenceAnswerDto
				.builder()
				.refId(referenceAnswer.getRefId())
				.adminId(referenceAnswer.getAdminId().getAdminId())
				.refAnswerContent(referenceAnswer.getRefAnswerContent())
				.refAnswerRegdate(referenceAnswer.getRefAnswerRegdate())
				.build();
	}
}
