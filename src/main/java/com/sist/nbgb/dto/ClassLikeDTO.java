package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassLike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassLikeDTO {
	private Long classId;  
	private String classIden;
	//private String userId;
	
//	public ClassLikeDTO toEntity(ClassLike classLike) {
//		return ClassLikeDTO.builder()
//				.classId(classLike.getClassId().getClassId())
//				.classIden(classLike.getClassId().getClassIden())
//				.userId(userId)
//				.build();
//	}
	
	public static ClassLikeDTO toEntity(ClassLike classLike) {
		return ClassLikeDTO.builder()
				.classId(classLike.getClassId().getClassId())
				.classIden(classLike.getClassId().getClassIden())
				//.userId(classLike.getUserId())
				.build();
	}
}
