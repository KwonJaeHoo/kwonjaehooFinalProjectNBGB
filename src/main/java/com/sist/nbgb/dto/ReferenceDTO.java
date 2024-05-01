package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Reference;
import com.sist.nbgb.entity.User;

import lombok.Getter;

@Getter
public class ReferenceDTO {
	private final Long refId;
	private final String refTitle;
	private final String refContent;
	private final User userId;
	private final LocalDateTime refRegdate;
	
	public ReferenceDTO(Reference ref)
	{
		this.refId = ref.getRefId();
		this.refTitle = ref.getRefTitle();
		this.refContent = ref.getRefContent();
		this.userId = ref.getUserId();
		this.refRegdate = ref.getRefRegdate();
	}
}
