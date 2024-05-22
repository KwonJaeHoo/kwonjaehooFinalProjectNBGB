package com.sist.nbgb.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
public class ChatMessageId implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private String chatId;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("메시지 번호")
	private Long messageId;
}
