package com.sist.nbgb.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_USER_FILE")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class UserFile 
{
	@EmbeddedId
	private UserFileId userFileId;
	
	@MapsId("userId")
	@Comment("사용자 아이디")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", columnDefinition = "VARCHAR2(20)")
	private User userId;
	
	@Column(length = 100)
	@Comment("원본 파일명")
	private String userFileOrgName;
	
	@Column(length = 50)
	@Comment("파일명")
	private String userFileName;
	
	@Column(length = 20)
	@Comment("파일 확장자")
	private String userFileExt;
	
	@Column
	@Comment("파일 크기")
	private String userFileSize;
	
	@CreatedDate
	@Comment("파일 등록일")
	private LocalTime userFileRegdate;
	
}
