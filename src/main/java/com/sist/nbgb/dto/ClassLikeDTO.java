package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassLike;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassLikeDTO {
	private Long classId;
	private String classIden;
	private String userId;
	
//	public ClassLikeDTO(ClassLike like) {
//		this.classId = like.getClassId().getClassId();
//		this.classIden = like.getClassId().getClassIden();
//		this.userId = like.getUserId().getUserId();
//	}
	
}
