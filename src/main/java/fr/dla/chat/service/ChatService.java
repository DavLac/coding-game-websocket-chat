package fr.dla.chat.service;

import fr.dla.chat.domain.ChatMessage;
import fr.dla.chat.domain.MessageEntity;
import fr.dla.chat.domain.UserEntity;
import fr.dla.chat.repository.MessageEntityRepository;
import fr.dla.chat.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatService {

    private static final String TOPIC_FORMAT = "/topic/%s";
    private static final String ROOM_ID = "room_id";
    private static final String USERNAME = "username";

    private final UserEntityRepository userEntityRepository;
    private final MessageEntityRepository messageEntityRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    //region public methods
    public void sendMessage(String roomId, ChatMessage chatMessage) {
        MessageEntity messageEntity = createMessage(roomId, chatMessage);
        messagingTemplate.convertAndSend("/topic/database-action",
            format("timestamp=%s :: a Message with ID=%d was inserted", Instant.now().toString(), messageEntity.getId()));
        messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
    }

    public void addUser(String roomId, ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        UserEntity userEntity = createUser(chatMessage.getSender());

        messagingTemplate.convertAndSend("/topic/database-action",
            format("timestamp=%s :: a User with ID=%d was inserted", Instant.now().toString(), userEntity.getId()));

        String currentRoomId = (String) headerAccessor.getSessionAttributes().put(ROOM_ID, roomId);
        if (currentRoomId != null) {
            ChatMessage leaveMessage = new ChatMessage();
            leaveMessage.setType(ChatMessage.MessageType.LEAVE);
            leaveMessage.setSender(chatMessage.getSender());
            messagingTemplate.convertAndSend(format(TOPIC_FORMAT, currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put(USERNAME, chatMessage.getSender());
        messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
    }
    //endregion public methods

    //region private methods
    private UserEntity createUser(String user) {
        return userEntityRepository.findByName(user)
            .orElseGet(() -> userEntityRepository.save(new UserEntity(user)));

    }

    private MessageEntity createMessage(String roomId, ChatMessage chatMessage) {
        UserEntity user = userEntityRepository.findByName(chatMessage.getSender()).orElse(null);
        return messageEntityRepository.save(new MessageEntity(chatMessage.getContent(), Instant.now(), roomId, user));
    }
    //endregion private methods
}
