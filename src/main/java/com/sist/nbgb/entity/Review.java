package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
@Table(name = "NBGB_REVIEW")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class Review
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("후기 글번호")
	private Long reviewId;
	
	@Comment("강의 번호")
	private Long classId;
	
	@Column(length = 3)
	@Comment("강의 식별자")
	private String classIden;
	
	@Column(columnDefinition = "NUMBER(1)")
	@Comment("후기 별점")
	private Long reviewRating;
	
	@Lob
	@Comment("후기 내용")
	private String reviewContent;
	
	@Comment("회원 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User userId;
	
	@CreatedDate
	@Comment("후기 작성일")
	private LocalDateTime reviewRegdate;
	
	@Column
	@Comment("후기 상태")
	@Enumerated(EnumType.STRING)
	private Status reviewStatus;
	
	@Column
	@Comment("좋아요 수")
	private Long reviewLikeCnt;
	
}
