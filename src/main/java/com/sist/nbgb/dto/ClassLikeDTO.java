package com.sist.nbgb.dto;

import com.sist.nbgb.entity.ClassLike;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OnlineClass;

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
	private int code;
	private String onlineClassTitle;
	private String offlineClassTitle;
	
	public static ClassLikeDTO toDto(ClassLike like) {
		return ClassLikeDTO.builder()
				.classId(like.getClassId().getClassId())
				.classIden(like.getClassId().getClassIden())
				.userId(like.getUserId().getUserId())
				.build();
	}

	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public void setOnlineClassTitle(OnlineClass onlineClass)
	{
		this.onlineClassTitle = onlineClass.getOnlineClassTitle();
	}
	
	public void setOfflineClassTitle(OfflineClass offlineClass)
	{
		this.offlineClassTitle = offlineClass.getOfflineClassTitle();
	}
	
	public ClassLikeDTO(ClassLike classLike)
	{
		this.classId = classLike.getClassId().getClassId();
		this.classIden = classLike.getClassId().getClassIden();
		this.userId = classLike.getUserId().getUserId();
	}
	
	public void setClassIden(String classIden)
	{
		this.classIden = classIden;
	}
}