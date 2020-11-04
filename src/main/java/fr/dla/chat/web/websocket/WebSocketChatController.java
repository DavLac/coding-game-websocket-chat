package fr.dla.chat.web.websocket;

import fr.dla.chat.domain.Message;
import fr.dla.chat.domain.User;
import fr.dla.chat.domain.WebSocketChatMessage;
import fr.dla.chat.repository.MessageEntityRepository;
import fr.dla.chat.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class WebSocketChatController {

    private final UserEntityRepository userEntityRepository;
    private final MessageEntityRepository messageEntityRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) throws Exception {
        User user = userEntityRepository.findByName(webSocketChatMessage.getSender()).orElseThrow(Exception::new);
        messageEntityRepository.save(new Message(webSocketChatMessage.getContent(), user));
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/public")
    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        userEntityRepository.findByName(webSocketChatMessage.getSender())
            .orElse(userEntityRepository.save(new User(webSocketChatMessage.getSender())));
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        return webSocketChatMessage;
    }
}
