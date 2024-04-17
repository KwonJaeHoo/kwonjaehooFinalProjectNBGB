package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name="NBGB_REFERENCE")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Reference 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("문의 게시물 번호")
	private Long refId;
	
	@Column(length = 150)
	@Comment("문의 제목")
	private String refTitle;
	
	@Lob
	@Comment("문의 내용")
	private String refContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@Comment("문의 작성자")
	private User UserId;
	
	@CreatedDate
	@Comment("문의 등록일")
	private LocalDateTime refRegdate;
}

