package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfflineClassIdDto 
{
	private Long classId;
	private String classIden;
	private String userId;
	
	public OfflineClassIdDto(ClassLikeDTO classLikeDto, String userId2)
	{
		this.classId = classLikeDto.getClassId();
		this.classIden = classLikeDto.getClassIden();
		this.userId = userId2;
	}
}
