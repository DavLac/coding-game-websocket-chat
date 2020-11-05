package fr.dla.chat.service;

import fr.dla.chat.domain.websocket.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static fr.dla.chat.config.Constants.ROOM_ID;
import static fr.dla.chat.config.Constants.TOPIC_FORMAT;
import static fr.dla.chat.config.Constants.USERNAME;
import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get(USERNAME);
        String roomId = (String) headerAccessor.getSessionAttributes().get(ROOM_ID);
        if (username != null) {
            log.info("User Disconnected: " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
        }
    }
}
