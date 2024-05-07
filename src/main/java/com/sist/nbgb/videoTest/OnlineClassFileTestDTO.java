package com.sist.nbgb.videoTest;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassFileId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OnlineClassFileTestDTO {
	private final OnlineClass onlineClassId;
	private final OnlineClassFileId onlineClassFileId;
	private final String onlineFileOrgName;
	private final String onlineFileName;
	private final String onlineFileExt;
	private final Long onlineFileSize;
	private final String onlineFileContent;
	
	public OnlineClassFile toEntity() {
		return OnlineClassFile.builder()
				.onlineClassId(onlineClassId)
				.onlineClassFileId(onlineClassFileId)
				.onlineFileOrgName(onlineFileOrgName)
				.onlineFileName(onlineFileName)
				.onlineFileExt(onlineFileExt)
				.onlineFileSize(onlineFileSize)
				.onlineFileContent(onlineFileContent)
				.onlineFileRegdate(LocalDateTime.now().withNano(0))
				.build();
	}

}
