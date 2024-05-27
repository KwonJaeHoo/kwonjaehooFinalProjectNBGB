package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//이거 필요없는듯
@Builder
@AllArgsConstructor
@Getter
public class OnlineClassFileDTO {
	private final Long onlineClassId;
	private final Long onlineFileId;
	private final Long onlineFileLength;
	private final String onlineFileOrgName;
	private final String onlineFileName;
	private final String onlineFileExt;
	private final Long onlineFileSize;
	private final String onlineFileContent;
	
	public static OnlineClassFileDTO toDto(OnlineClassFileDTO dto) {
		return OnlineClassFileDTO.builder()
				.onlineClassId(dto.getOnlineClassId())
				.onlineFileId(dto.getOnlineFileId())
				.onlineFileLength(dto.getOnlineFileLength())
				.onlineFileOrgName(dto.getOnlineFileOrgName())
				.onlineFileName(dto.getOnlineFileName())
				.onlineFileExt(dto.getOnlineFileExt())
				.onlineFileSize(dto.getOnlineFileSize())
				.onlineFileContent(dto.getOnlineFileContent())
				.build();
	}
}
