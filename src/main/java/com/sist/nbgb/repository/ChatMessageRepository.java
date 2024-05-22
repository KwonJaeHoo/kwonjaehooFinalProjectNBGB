package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.nbgb.entity.Chat;
import com.sist.nbgb.entity.ChatMessage;
import com.sist.nbgb.entity.ChatMessageId;
import com.sist.nbgb.entity.Instructors;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, ChatMessageId>
{
    Long countByChatId(Chat chatId);
    
    List<ChatMessage> findAllByChatIdOrderByMessageRegdateAsc(Chat chatId);
    
    @Query(value = "SELECT * FROM ( "
    		+ "	        SELECT CHAT_ID, MESSAGE_ID, MESSAGE_CONTENT, MESSAGE_READ, MESSAGE_REGDATE, SEND_ID, INSTRUCTOR_ID, USER_ID, ROW_NUMBER() OVER (PARTITION BY CHAT_ID ORDER BY MESSAGE_REGDATE DESC) AS rn "
    		+ "	        FROM NBGB_CHAT_MESSAGE WHERE CHAT_ID = :chatId) "
    		+ "	        WHERE rn = 1", nativeQuery = true)
    ChatMessage lastChat(@Param("chatId") String chatId);
 	
	@Modifying
	@Query("UPDATE ChatMessage " + "SET messageRead = 'Y' "
			+ "WHERE chatId = :chatId "
			+ "AND messageRead = 'N'"
			+ "AND sendId = :instructorId") 
	int updateInstructorRead(@Param("chatId") Chat chatId, @Param("instructorId") String instructorId);
}
