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
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ClassId implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Comment("강의 게시물 번호")
	private Long classId;  
	
	@Column(length = 3)
	@Comment("강의 식별자")
	private String classIden;
	
	private String userId;
}
