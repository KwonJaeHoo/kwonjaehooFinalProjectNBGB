package com.sist.nbgb.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name="NBGB_REVIEW_REPORT")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ReviewReport 
{
	@EmbeddedId 
	private ReviewReportId id;
	
	@MapsId("reviewId")
	@Comment("후기 게시물 번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="review_Id")
	private Review reviewId;
	
	@MapsId("userId")
	@Comment("신고자 회원 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_Id", columnDefinition="varchar2(20)")
	private User userId;
	
	@CreatedDate
	@Comment("신고 일자")
	private LocalTime reportDate;
	
	@Column(length = 30)
	@Comment("신고 사유")
	private String reportReason;
	
	@Column(columnDefinition = "CHAR(1)")
	@Comment("신고 처리상태")
	@Enumerated(EnumType.STRING)
	private Status reportStatus;
}
