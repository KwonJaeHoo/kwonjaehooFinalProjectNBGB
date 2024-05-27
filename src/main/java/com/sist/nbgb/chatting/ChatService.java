package com.sist.nbgb.chatting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.ChatMessage;
import com.sist.nbgb.entity.ChatMessageId;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.ChatMessageRepository;
import com.sist.nbgb.repository.ChatRepository;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.UserRepository;
import com.sist.nbgb.response.UUIDUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService 
{

    private final ObjectMapper objectMapper;
    
    private Map<String, ChattingRoom> chatRooms;
    
    private final UserRepository userRepository;
    
    private final InstructorsRepository instuctorsRepository;
    
    private final ChatRepository chatRepository;
    
    private final ChatMessageRepository messageRepository;

    @PostConstruct
    private void init() 
    {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChattingRoom> findAllRoom()
    {
        return new ArrayList<>(chatRooms.values());
    }

    public Chat findChatBychatId(String chatId)
    {
        return chatRepository.findChatByChatId(chatId);
    }

    @Transactional
    public ChattingRoom createRoom(String inst, String user)
    {
        String randomId = UUIDUtil.uniqueValue();
        
        User userId = userRepository.findFirstByUserId(user);
        
        Instructors instructorId = instuctorsRepository.findFirstByInstructorId(inst);
        
        Chat chat = Chat.builder()
        		.chatId(randomId)
        		.userId(userId)
        		.instructorId(instructorId)
        		.chatRegdate(LocalDateTime.now())
        		.build();
        		
        ChattingRoom chatRoom = ChattingRoom.builder()
        		.chatId(randomId)
        		.userId(userId)
        		.instructorId(instructorId)
        		.build();
        
        Chat createChat = chatRepository.save(chat);
        
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }
    
    public Chat findByInstructorIdAndUserId(String inst, String user)
    {

        User userId = userRepository.findFirstByUserId(user);
        
        Instructors instructorId = instuctorsRepository.findFirstByInstructorId(inst);
    	
    	return chatRepository.findByInstructorIdAndUserId(instructorId, userId);
    }
    
    //채팅 DB 저장
    @Transactional
    public ChatMessage insertChatting(Chat chat, Message message)
    {    	
    	Chat chatId = chatRepository.findFirstByChatId(chat.getChatId());
    	
    	Long id = messageRepository.countByChatId(chatId);
    	
    	log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + id);
    	
    	ChatMessageId messageId = ChatMessageId.builder()
    			.messageId(id + 1)
    			.build();
    			
    	ChatMessage chatMessage = ChatMessage.builder()
    			.id(messageId)
    			.chatId(chatId)
    			.messageContent(message.getMessage())
    			.messageRead(Status.N)
    			.messageRegdate(LocalDateTime.now())
    			.userId(chat.getUserId())
    			.instructorId(chat.getInstructorId())
    			.sendId(message.getSender())
    			.build();
    	
    	return messageRepository.save(chatMessage);
    }
    
    //채팅창 리스트
    public List<ChatMessage> findMessageList(String id)
    {
    	Chat chatId = chatRepository.findFirstByChatId(id);
    	
    	return messageRepository.findAllByChatIdOrderByMessageRegdateAsc(chatId);
    }
    
    //채팅방 리스트(사용자)
    public List<Chat> listUserId(String userId)
    {
    	return chatRepository.listUserId(userId);
    }
	
    //채팅방 리스트(강사)
	public List<Chat> listInstructorId(String instructorId)
	{
		return chatRepository.listInstructorId(instructorId);
	}
	
	//마지막 채팅 검색
	public ChatMessage lastChat(String chatId)
	{
		return messageRepository.lastChat(chatId);
	}
		 
	//채팅 읽음 update
	@Transactional
	public int updateRead(String id, String sendId) 
	{
		Chat chatId = chatRepository.findFirstByChatId(id);
				
		return messageRepository.updateInstructorRead(chatId, sendId);
	}
}