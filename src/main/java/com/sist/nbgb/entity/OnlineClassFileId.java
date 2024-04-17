package com.sist.nbgb.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class OnlineClassFileId implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Comment("온라인 강의 번호")
	private Long onlineClassId;
	
	@Comment("첨부 파일 번호")
	private Long onlineFileId;
	
	@Comment("동영상 길이")
	private Long onlineFileLength;
}
