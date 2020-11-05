package fr.dla.chat.service;

import fr.dla.chat.domain.entities.MessageEntity;
import fr.dla.chat.domain.entities.UserEntity;
import fr.dla.chat.domain.websocket.ChatMessage;
import fr.dla.chat.repository.MessageEntityRepository;
import fr.dla.chat.repository.UserEntityRepository;
import fr.dla.chat.web.error.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static fr.dla.chat.config.Constants.ROOM_ID;
import static fr.dla.chat.config.Constants.TOPIC_FORMAT;
import static fr.dla.chat.config.Constants.USERNAME;
import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ChatService {

    public static final String USER_NOT_EXIST_ERROR_KEY = "userNotExistError";
    private final UserEntityRepository userEntityRepository;
    private final MessageEntityRepository messageEntityRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    //region public methods
    public void sendMessage(String roomId, ChatMessage chatMessage) {
        createMessage(roomId, chatMessage);
        messagingTemplate.convertAndSend(format(TOPIC_FORMAT, roomId), chatMessage);
    }

    public void addUser(String roomId, ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        createUserIfNotExist(chatMessage.getSender());

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
    private void createUserIfNotExist(String user) {
        // in this app, we consider users 'name' are unique in database
        userEntityRepository.findByName(user)
            .orElseGet(() -> userEntityRepository.save(new UserEntity(user)));
    }

    private void createMessage(String roomId, ChatMessage chatMessage) {
        UserEntity user = userEntityRepository.findByName(chatMessage.getSender())
            .orElseThrow(() -> new InternalServerErrorException("User not exist", "user", USER_NOT_EXIST_ERROR_KEY));
        messageEntityRepository.save(new MessageEntity(chatMessage.getContent(), Instant.now(), roomId, user));
    }
    //endregion private methods
}
