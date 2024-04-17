package com.sist.nbgb.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
public class ReiewFileId implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Column
	@Comment("첨부파일 번호")
	private Long reviewFileId;

	private Long reviewId;
}
