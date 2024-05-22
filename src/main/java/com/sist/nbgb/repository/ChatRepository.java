package com.sist.nbgb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String>
{
	Chat findByInstructorIdAndUserId(Instructors instructorId, User userId);
	
	Chat findChatByChatId(String chatId);
	
	Chat findFirstByChatId(String chatId);
}
