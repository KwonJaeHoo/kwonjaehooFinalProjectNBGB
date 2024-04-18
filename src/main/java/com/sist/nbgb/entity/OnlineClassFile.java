package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "NBGB_ONLINE_CLASS_FILE")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class OnlineClassFile 
{
	@EmbeddedId
	private OnlineClassFileId onlineClassFileId;
	
	@MapsId("onlineClassId")
	@Comment("온라인 강의 번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "onlieClassId")
	private OnlineClass onlineClassId;
	
	@Column(length = 100)
	@Comment("원본 파일명")
	private String onlineFileOrgName;
	
	@Column(length = 50)
	@Comment("파일 명")
	private String onlineFileName;
	
	@Column(length = 20)
	@Comment("파일 확장자")
	private String onlineFileExt;
	
	@Column
	@Comment("파일 크기")
	private Long onlineFileSize;
	
	@CreatedDate
	@Comment("파일 등록 일자")
	private LocalDateTime onlineFileRegdate;
	
	@Column(length = 100)
	@Comment("동영상 설명")
	private String onlineFileContent;
}