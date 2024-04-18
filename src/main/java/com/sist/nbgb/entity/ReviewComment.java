package com.sist.nbgb.entity;

import org.springframework.data.annotation.CreatedDate;

import com.sist.nbgb.enums.Status;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_REVIEW_COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ReviewComment 
{
	@EmbeddedId
	private ReviewId id;
	
	@MapsId("reviewId")
	@Comment("후기 글번호")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEW_ID")
	private Review reviewId;
	
	@Lob
	@Comment("후기 댓글 내용")
	private String reviewCommentContent;
	
	@Comment("강사 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Instructors instructorId;
	
	@CreatedDate
	@Comment("후기 댓글 작성일")
	private LocalDateTime reviewCommentRegdate;
	
	@Column
	@Comment("후기 댓글 상태")
	@Enumerated(EnumType.STRING)
	private Status reviewCommentStatus;
	
}