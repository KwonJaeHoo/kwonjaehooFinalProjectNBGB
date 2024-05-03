package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.User;

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
public class ReferenceDTO {
	private Long refId;
	
	private String refTitle;
	private String refContent;
	private LocalDateTime refRegdate;
	private User userId;
	
	public static ReferenceDTO referenceDTO(Reference ref)
	{
		return ReferenceDTO.builder()
				.refTitle(ref.getRefTitle())
				.refContent(ref.getRefContent())
				.refRegdate(ref.getRefRegdate())
				.userId(ref.getUserId())
				.build();
	}
	
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
}