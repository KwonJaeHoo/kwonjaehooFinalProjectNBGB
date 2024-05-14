package com.sist.nbgb.dto;

import java.time.LocalDateTime;
import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.ReferenceAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReferenceDto2 
{
	private Reference refId;
	private String userId;
	private String refTitle;
	private String refContent;
	private LocalDateTime refRegdate;
	
	private ReferenceAnswer referenceAnswer;
	
	public ReferenceDto2(Reference reference)
	{
		this.refId = reference;
		this.userId = reference.getUserId().getUserId();
		this.refTitle = reference.getRefTitle();
		this.refContent = reference.getRefContent();
		this.refRegdate = reference.getRefRegdate();
	}
	
	public void setReferenceAnswer(ReferenceAnswer referenceAnswer)
	{
		this.referenceAnswer = referenceAnswer;
	}
}
