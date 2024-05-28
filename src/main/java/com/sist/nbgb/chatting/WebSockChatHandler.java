package com.sist.nbgb.chatting;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.nbgb.entity.Chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<String, Set<WebSocketSession>> roomSessions = new HashMap<>();
    private final Map<WebSocketSession, String> sessionRoomMap = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결이 성립되었을 때 실행되는 로직 (필요 시 구현)
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Message chatMessage = objectMapper.readValue(payload, Message.class);
        String chatId = chatMessage.getChatId();
        Chat room = chatService.findChatBychatId(chatId);
        
        if(room.getUserId().getUserId().equals(chatMessage.getSender()) || room.getInstructorId().getInstructorId().equals(chatMessage.getSender()))
        {
        	// chatId로 세션들을 관리하는 로직 추가
            roomSessions.putIfAbsent(chatId, Collections.synchronizedSet(new HashSet<>()));
            Set<WebSocketSession> sessions = roomSessions.get(chatId);

            log.info("Payload: {}", chatMessage.getSender());

           sendToEachSocket(chatId, message);
           chatService.insertChatting(room, chatMessage);
           
        }
    }

    private void sendToEachSocket(String chatId, TextMessage message) {
        Set<WebSocketSession> sessions = roomSessions.get(chatId);
        if (sessions != null) {
            sessions.parallelStream().forEach(roomSession -> {
                try {
                    roomSession.sendMessage(message);
                } catch (IOException e) {
                    log.error("Failed to send message: {}", message.getPayload(), e);
                    throw new RuntimeException(e);
                }
            });
            log.info("Message sent: {}", message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 끊겼을 때 실행되는 로직
        String chatId = sessionRoomMap.get(session);
        if (chatId != null) {
            Set<WebSocketSession> sessions = roomSessions.get(chatId);
            if (sessions != null) {
                sessions.remove(session);
            }
            sessionRoomMap.remove(session);
        }
    }
}