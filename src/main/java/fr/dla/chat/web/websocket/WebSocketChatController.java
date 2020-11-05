package fr.dla.chat.web.websocket;

import fr.dla.chat.domain.ChatMessage;
import fr.dla.chat.domain.MessageEntity;
import fr.dla.chat.domain.UserEntity;
import fr.dla.chat.repository.MessageEntityRepository;
import fr.dla.chat.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Optional;

import static java.lang.String.format;

@RequiredArgsConstructor
@Controller
public class WebSocketChatController {

    private static final String TOPIC_FORMAT = "/topic/%s";

    private final UserEntityRepository userEntityRepository;
    private final MessageEntityRepository messageEntityRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) throws Exception {
        UserEntity user = userEntityRepository.findByName(chatMessage.getSender()).orElseThrow(Exception::new);
        messageEntityRepository.save(new MessageEntity(chatMessage.getContent(), Instant.now(), roomId, user));

        messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {

        Optional<UserEntity> userEntityOptional =  userEntityRepository.findByName(chatMessage.getSender());
         if(!userEntityOptional.isPresent()){
             userEntityRepository.save(new UserEntity(chatMessage.getSender()));
         }

        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            ChatMessage leaveMessage = new ChatMessage();
            leaveMessage.setType(ChatMessage.MessageType.LEAVE);
            leaveMessage.setSender(chatMessage.getSender());
            messagingTemplate.convertAndSend(format(TOPIC_FORMAT, currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
    }
}
