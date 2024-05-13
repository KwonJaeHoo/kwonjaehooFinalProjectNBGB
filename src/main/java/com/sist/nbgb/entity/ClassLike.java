package com.sist.nbgb.entity;

import javax.persistence.Embeddable;
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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NBGB_CLASS_LIKE")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ClassLike 
{
	@EmbeddedId
	private ClassId classId;
	
	@MapsId("userId")
	@Comment("사용자 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", columnDefinition = "VARCHAR2(20)")
	private User userId;
	
}