package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfflineClassDenyDTO {
	private long offlineClassId;
	private String rejection;
	private Status offlineClassApprove;
	private LocalDateTime offlineRegdate;
	
	public OfflineClassDenyDTO(OfflineClass offlineClass)
	{
		this.offlineClassId = offlineClass.getOfflineClassId();
		this.rejection = offlineClass.getRejection();
		this.offlineClassApprove = offlineClass.getOfflineClassApprove();
		this.offlineRegdate = offlineClass.getOfflineClassRegdate();
	}
}
