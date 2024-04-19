package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_ONLINE_CLASS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OnlineClass 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("온라인 강의 글번호")
	private Long onlineClassId;
	
	@Column(length = 150)
	@Comment("온라인 강의 제목")
	private String onlineClassTitle;
	
	@Lob
	@Comment("온라인 강의 내용")
	private String onlineClassContent;
	
	@CreatedDate
	@Comment("온라인 강의 게시일")
	private LocalDateTime onlineClassRegdate;
	
	@Comment("온라인 강의 카테고리 번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ONLINE_CATEGORY")
	private OnlineCategory onlineCategoryId;
	
	@Comment("강사 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Instructors instructorId;
	
	@Column
	@Comment("온라인 강의 가격")
	private Long onlineClassPrice;
	
	@Column
	@Comment("온라인 강의 기간")
	private Long onlineClassPeriod;
	
	@Column
	@Comment("온라인 강의 승인")
	@Enumerated(EnumType.STRING)
	private Status onlineClassApprove;
	
	@Column
	@Comment("반려사유")
	private String rejection;
	
	@CreatedDate
	@Comment("반려날짜")
	private LocalDateTime rejectionRedgate;
	
	@Column
	@Comment("온라인 강의 조회수")
	private Long onlineClassViews;
	
}
