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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NBGB_OFFLINE_CLASS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflineClass 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("오프라인 강의 글번호")
	private Long offlineClassId;
	
	@Column(length = 150)
	@Comment("오프라인 강의 제목")
	private String offlineClassTitle;
	
	@Lob
	@Comment("오프라인 강의 내용")
	private String offlineClassContent;
	
	@CreatedDate
	@Comment("오프라인 강의 게시일")
	private LocalDateTime offlineClassRegdate;
	
	@Column(length = 150)
	@Comment("오프라인 강의 장소")
	private String offlineClassPlace;
	
	@Column(length = 150)
	@Comment("오프라인 강의 카테고리")
	private String offlineClassCategory;
	
	@Comment("오프라인 강의 강사 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Instructors instructorId;
	
	@Column
	@Comment("오프라인 강의 가격")
	private Long offlineClassPrice;
	
	@Column
	@Comment("오프라인 강의 승인")
	@Enumerated(EnumType.STRING)
	private Status offlineClassApprove;
	
	@Column
	@Comment("반려사유")
	private String rejection;
	
	@CreatedDate
	@Comment("반려날짜")
	private LocalDateTime rejectionRegdate;
	
	@Column(columnDefinition = "NUMBER(3)")
	@Comment("오프라인 강의 참여 가능 인원수")
	private Long offlineClassLimitPeople;
	
	@Column(columnDefinition = "NUMBER(6)")
	@Comment("오프라인 강의 조회수")
	private Long offlineClassViews;
	
	public void update(String title, String content, String category, Long limit, Long price, String place)
	{
		this.offlineClassTitle = title;
		this.offlineClassContent = content;
		this.offlineClassCategory = category;
		this.offlineClassLimitPeople = limit;
		this.offlineClassPrice = price;
		this.offlineClassPlace = place;
		this.offlineClassApprove = Status.N;
	}
}
