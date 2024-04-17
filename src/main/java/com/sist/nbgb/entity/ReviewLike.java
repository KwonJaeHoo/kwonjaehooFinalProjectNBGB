package com.sist.nbgb.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_REVIEW_LIKE")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReviewLike 
{
	@EmbeddedId
	private ReviewLikeId reviewLikeId;
	
	@MapsId("reviewId")
	@Comment("후기 글번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEW_ID")
	private Review reviewId;
	
	@MapsId("userId")
	@Comment("회원 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", columnDefinition = "VARCHAR2(20)")
	private User userId;
}
