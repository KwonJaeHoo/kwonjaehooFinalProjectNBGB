package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
