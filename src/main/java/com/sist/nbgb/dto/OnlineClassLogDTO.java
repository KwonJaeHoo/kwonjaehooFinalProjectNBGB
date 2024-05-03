package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OnlineClassLogDTO {
	private final OnlineClassLogId onlineClassLogId;
	private final User userId;
	private final OnlineClassFile onlineClassFile;
	private final Long onlineLogCurr;
	private final LocalDateTime onlineLogDate;
	private final Status status;
	
	public OnlineClassLog toEntity() {
		return OnlineClassLog.builder()
				.onlineClassLogId(onlineClassLogId)
				.userId(userId)
				.onlineClassFile(onlineClassFile)
				.onlineLogCurr(onlineLogCurr)
				.onlineLogDate(LocalDateTime.now().withNano(0))
				.status(status)
				.build();
	};
	
}
