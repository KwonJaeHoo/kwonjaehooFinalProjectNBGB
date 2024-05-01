package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;

import lombok.Getter;

@Getter
public class OnlineClassFileResponseDTO {
	private final OnlineClass onlineClassId;
	private final OnlineClassFileId onlineClassFileId;
	private final String onlineFileOrgName;
	private final String onlineFileName;
	private final String onlineFileExt;
	private final Long onlineFileSize;
	private final String onlineFileContent;
	
	public OnlineClassFileResponseDTO(OnlineClassFile onlineClassFile) {
		this.onlineClassId = onlineClassFile.getOnlineClassId();
		this.onlineClassFileId = onlineClassFile.getOnlineClassFileId();
		this.onlineFileOrgName = onlineClassFile.getOnlineFileOrgName();
		this.onlineFileName = onlineClassFile.getOnlineFileName();
		this.onlineFileExt = onlineClassFile.getOnlineFileExt();
		this.onlineFileSize = onlineClassFile.getOnlineFileSize();
		this.onlineFileContent = onlineClassFile.getOnlineFileContent();
	}
}
