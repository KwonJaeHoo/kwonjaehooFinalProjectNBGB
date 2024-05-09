package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OnlineClassFileId;
import com.sist.nbgb.entity.OnlineClassLogId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OnlineClassLogIdDTO {
	private String userId;
	private Long onlineClassId;
	private Long onlineFileId;
	
	public OnlineClassLogId toEntity() {
		return OnlineClassLogId.builder()
				.userId(userId)
				.onlineClassFileId(OnlineClassFileId.builder().onlineClassId(onlineClassId).onlineFileId(onlineFileId).onlineFileLength(Long.valueOf(180)).build())
				.build();
	}
}