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
	private final String userId;
	private final Long onlineClassId;
	private final Long onlineFileId;
	
	public OnlineClassLogId toEntity() {
		return OnlineClassLogId.builder()
				.userId(userId)
				.onlineClassFileId(OnlineClassFileId.builder().onlineClassId(onlineClassId).onlineFileId(onlineFileId).onlineFileLength(Long.valueOf(180)).build())
				.build();
	}
}
