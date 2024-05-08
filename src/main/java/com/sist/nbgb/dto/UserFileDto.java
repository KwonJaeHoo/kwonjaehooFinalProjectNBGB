package com.sist.nbgb.dto;

import java.util.List;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.entity.UserFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFileDto 
{	
	private User userId;
	
	private String userFileOrgName;
	
	private String userFileName;
	
	private String userFileExt;
	
	private Long userFileSize;
	
	public static UserFileDto fileIn(UserFile userFile)
	{
		return UserFileDto
				.builder()
				.userId(userFile.getUserId())
				.userFileOrgName(userFile.getUserFileOrgName())
				.userFileName(userFile.getUserFileName())
				.userFileExt(userFile.getUserFileExt())
				.userFileSize(userFile.getUserFileSize())
				.build();
	}
	
	public static UserFileDto fileOut(List<UserFile> userFile)
	{
		return UserFileDto
				.builder()
				.userId(userFile.get(0).getUserId())
				.userFileName(userFile.get(0).getUserFileName())
				.userFileExt(userFile.get(0).getUserFileExt())
				.build();
	}
}
