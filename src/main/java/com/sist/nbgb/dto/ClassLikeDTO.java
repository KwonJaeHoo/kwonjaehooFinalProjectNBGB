package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassLike;

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
public class ClassLikeDTO {
	private Long classId;
	private String classIden;
	private String userId;
	
	public static ClassLikeDTO toDto(ClassLike like) {
		return ClassLikeDTO.builder()
				.classId(like.getClassId().getClassId())
				.classIden(like.getClassId().getClassIden())
				.userId(like.getUserId().getUserId())
				.build();
	}
	
}
