package com.sist.nbgb.chatting;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.ChatMessage;
import com.sist.nbgb.entity.ChatMessageId;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class ChatMessageDto 
{
	private ChatMessageId id;
	
	private Chat chatId;

	private String messageContent;
	
	private Status messageRead;
	
	private LocalDateTime messageRegdate;

	private User userId;

	private Instructors instructorId;
	
	private String userNickname;
	
	private String instructorNickname;
	
	private String sendId;
	
	public ChatMessageDto(ChatMessage message)
	{
		this.id = message.getId();
		
		this.chatId = message.getChatId();

		this.messageContent = message.getMessageContent();
		
		this.messageRead = message.getMessageRead();
		
		this.messageRegdate = message.getMessageRegdate();

		this.userId = message.getUserId();

		this.instructorId = message.getInstructorId();
		
		this.userNickname = message.getUserId().getUserNickname();
		
		this.instructorNickname = message.getInstructorId().getInstructorNickname();
		
		this.sendId = message.getSendId();
	}
}
