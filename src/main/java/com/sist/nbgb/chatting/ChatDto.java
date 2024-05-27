package com.sist.nbgb.chatting;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;

import lombok.Getter;

@Getter
public class ChatDto 
{
	private String chatId;
	
	private LocalDateTime chatRegdate;
	
	private User userId;
	
	private Instructors instructorId;
	
	private String userNickname;
	
	private String insturctorNickname;
	
	private String lastChat;
	
	private String messageRead;
	
	private String img;
	
	public ChatDto(Chat chat)
	{
		this.chatId = chat.getChatId();
		
		this.chatRegdate = chat.getChatRegdate();
		
		this.userId = chat.getUserId();
		
		this.instructorId = chat.getInstructorId();
		
		this.userNickname = chat.getUserId().getUserNickname();
		
		this.insturctorNickname = chat.getInstructorId().getInstructorNickname();
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public void setLastChat(String lastChat) {
		this.lastChat = lastChat;
	}

	public void setMessageRead(String messageRead) {
		this.messageRead = messageRead;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
