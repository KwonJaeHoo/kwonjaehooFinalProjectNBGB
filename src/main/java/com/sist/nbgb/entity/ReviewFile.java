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
@Table(name = "NBGB_REVIEW_FILE")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ReviewFile 
{
	@EmbeddedId
	private ReviewFileId reviewFileId;
	
	@MapsId("reviewId")
	@Comment("후기 게시물 번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewId")
	private Review reviewId;
	
	@Column(length = 100)
	@Comment("원본 파일명")
	private String reviewFileOrgName;

	@Column(length = 50)
	@Comment("파일 명")
	private String reviewFileName;
	
	@Column(length = 20)
	@Comment("파일 확장자")
	private String reviewFileExt;
	
	@Column
	@Comment("파일 크기")
	private Long reviewFileSize;
	
	@CreatedDate
	@Comment("파일 등록일")
	private LocalDateTime reviewFileRegdate;
}
