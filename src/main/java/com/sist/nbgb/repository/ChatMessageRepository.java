package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.ChatMessage;
import com.sist.nbgb.entity.ChatMessageId;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, ChatMessageId>
{
    Long countByChatId(Chat chatId);
    
    List<ChatMessage> findAllByChatIdOrderByMessageRegdateAsc(Chat chatId);
}
