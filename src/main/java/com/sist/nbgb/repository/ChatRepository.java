package com.sist.nbgb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query(value = "SELECT a.* FROM NBGB_CHAT a JOIN ( "
			+ "	        SELECT CHAT_ID, MESSAGE_REGDATE, ROW_NUMBER() OVER (PARTITION BY CHAT_ID ORDER BY MESSAGE_REGDATE DESC) AS rn "
			+ "	        FROM NBGB_CHAT_MESSAGE WHERE USER_ID = :userId) b "
			+ "	        ON a.CHAT_ID = b.CHAT_ID WHERE b.rn = 1 ORDER BY b.MESSAGE_REGDATE DESC", nativeQuery = true)
	List<Chat> listUserId(@Param("userId") String userId);
	
	@Query(value = "SELECT a.* FROM NBGB_CHAT a JOIN ( "
			+ "	        SELECT CHAT_ID, MESSAGE_REGDATE, ROW_NUMBER() OVER (PARTITION BY CHAT_ID ORDER BY MESSAGE_REGDATE DESC) AS rn "
			+ "	        FROM NBGB_CHAT_MESSAGE WHERE INSTRUCTOR_ID = :instructorId) b "
			+ "	        ON a.CHAT_ID = b.CHAT_ID WHERE b.rn = 1 ORDER BY b.MESSAGE_REGDATE DESC", nativeQuery = true)
	List<Chat> listInstructorId(@Param("instructorId") String instructorId);
}
