package com.sist.nbgb.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="NBGB_CHAT")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Chat 
{
	@Id
	@Comment("채팅방 번호")
	private Long chatId;
	
	@CreatedDate
	@Comment("채팅방 생성일")
	private LocalTime chatRegdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@Comment("개설자 회원 아이디")
	private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="instructorId")
	@Comment("참여자 강사 아이디")
	private Instructors instructorId;
}
