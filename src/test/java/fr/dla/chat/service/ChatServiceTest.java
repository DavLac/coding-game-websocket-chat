package fr.dla.chat.service;

import fr.dla.chat.domain.entities.UserEntity;
import fr.dla.chat.domain.websocket.ChatMessage;
import fr.dla.chat.repository.MessageEntityRepository;
import fr.dla.chat.repository.UserEntityRepository;
import fr.dla.chat.web.error.InternalServerErrorException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.HashMap;
import java.util.Optional;

import static fr.dla.chat.service.ChatService.USER_NOT_EXIST_ERROR_KEY;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    private static final String USER = "user";
    private static final String ROOM_ID = "roomId";
    public static final String CONTENT = "content";

    @Mock
    private UserEntityRepository userEntityRepository;
    @Mock
    private MessageEntityRepository messageEntityRepository;
    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @InjectMocks
    private ChatService chatService;

    @Test
    void addUser_withSender_shouldCreateUser() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(USER);
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();
        simpMessageHeaderAccessor.setSessionAttributes(new HashMap<>());

        Mockito.when(userEntityRepository.findByName(USER)).thenReturn(Optional.empty());

        chatService.addUser(ROOM_ID, chatMessage, simpMessageHeaderAccessor);

        verify(userEntityRepository).findByName(USER);
    }

    @Test
    void addUser_withSenderAlreadyExist_shouldNotCreateUser() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(USER);
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();
        simpMessageHeaderAccessor.setSessionAttributes(new HashMap<>());

        Mockito.when(userEntityRepository.findByName(USER)).thenReturn(Optional.of(new UserEntity()));

        chatService.addUser(ROOM_ID, chatMessage, simpMessageHeaderAccessor);

        verify(userEntityRepository).findByName(USER);
    }

    @Test
    void sendMessage_withNoUser_shouldThrowInternalError() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(USER);
        chatMessage.setContent(CONTENT);
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();
        simpMessageHeaderAccessor.setSessionAttributes(new HashMap<>());

        Mockito.when(userEntityRepository.findByName(USER)).thenReturn(Optional.empty());

        try {
            chatService.sendMessage(ROOM_ID, chatMessage);
        } catch (InternalServerErrorException ex) {
            Assert.assertEquals(USER_NOT_EXIST_ERROR_KEY, ex.getErrorKey());
        }
    }

    @Test
    void sendMessage_withSender_shouldSendMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(USER);
        chatMessage.setContent(CONTENT);
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();
        simpMessageHeaderAccessor.setSessionAttributes(new HashMap<>());

        Mockito.when(userEntityRepository.findByName(USER)).thenReturn(Optional.of(new UserEntity()));

        chatService.sendMessage(ROOM_ID, chatMessage);

        verify(userEntityRepository).findByName(USER);
    }
}
