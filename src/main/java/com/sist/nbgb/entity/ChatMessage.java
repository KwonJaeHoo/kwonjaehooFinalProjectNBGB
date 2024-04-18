package com.sist.nbgb.entity;

import java.time.LocalDateTime;

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
@Table(name="NBGB_CHAT_MESSAGE")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ChatMessage 
{
	@EmbeddedId
	private ChatMessageId id;

	@MapsId("chatId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="chat_Id")
	@Comment("채팅방 번호")
	private Chat chat;

	@Column(length = 4000)
	@Comment("메시지 내용")
	private String messageContent;
	
	@Comment("읽음 여부(Y, N)")
	@Enumerated(EnumType.STRING)
	private Status messageRead;
	
	@CreatedDate
	@Comment("메시지 보낸 시간")
	private LocalDateTime messageRegdate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@Comment("보낸 사람 아이디(회원)")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="instructorId")
	@Comment("보낸 사람 아이디(강사)")
	private Instructors instructorId;
}
