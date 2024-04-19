package com.sist.nbgb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "NBGB_ONLINE_CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OnlineCategory 
{
	@Id
	@Column(columnDefinition = "NUMBER(2)")
	@Comment("온라인 강의 카테고리 번호")
	private Long onlineCategoryId;
	
	@Column(length = 30)
	@Comment("온라인 강의 카테고리 내용")
	private String onlineCategoryContent;
	
}
