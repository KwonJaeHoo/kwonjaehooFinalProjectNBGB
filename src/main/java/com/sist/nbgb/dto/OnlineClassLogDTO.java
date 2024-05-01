package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlineClassLogDTO {
	private User userId;
	private Long onlineLogCurr;
	
	public OnlineClassLog toEntity() {
		return OnlineClassLog.builder()
				.onlineLogCurr(onlineLogCurr)
				.build();
	};
	
}
