package com.sist.nbgb.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import com.sist.nbgb.entity.User;
import com.sist.nbgb.entity.UserFile;
import com.sist.nbgb.entity.UserFileId;

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
