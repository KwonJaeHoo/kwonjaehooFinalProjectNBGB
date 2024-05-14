package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfflineClassStatusChange {
	
	private long offlineClassId;
	private String rejection;
}
