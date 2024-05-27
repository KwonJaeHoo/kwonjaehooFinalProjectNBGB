package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class OnlineClassLogReqDTO {
	private Long onlineLogCurr;
	private LocalDateTime onlineLogDate;
	private Status status;
	
	public OnlineClassLogReqDTO(Long onlineLogCurr, Status status) {
		this.onlineLogCurr = onlineLogCurr;
		this.onlineLogDate = LocalDateTime.now().withNano(0);
		this.status = status;
	}
}
