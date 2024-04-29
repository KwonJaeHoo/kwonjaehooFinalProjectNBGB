package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;
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
public class OfflineLikeDto
{
	private ClassId classId;
	private User userId;
	
	public OfflineLikeDto(ClassLike like)
	{
		this.classId = like.getClassId();
		this.userId = like.getUserId();
	}
	
	public static OfflineLikeDto toDto(ClassLike classLike)
	{
		return OfflineLikeDto.builder()
				.classId(classLike.getClassId())
				.userId(classLike.getUserId())
				.build();
	}

	public static OfflineLikeDto createClassLike(OfflineClassIdDto likeDto) {
		OfflineLikeDto like = new OfflineLikeDto();
		like.classId.setClassId(likeDto.getClassId());
		like.classId.setClassIden(likeDto.getClassIden());
		like.userId.setUserId(likeDto.getUserId());
		return like;
	}	
}
