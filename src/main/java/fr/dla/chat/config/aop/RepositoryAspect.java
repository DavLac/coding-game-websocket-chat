package fr.dla.chat.config.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static fr.dla.chat.config.Constants.TOPIC_DATABASE_LOG;
import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class RepositoryAspect {

    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Pointcut that matches save methods in repositories in repository package
     * Used to send message when a repository save any entity in database
     */
    @After("execution(* fr.dla.chat.repository.*Repository.save(..)) && args(entity)")
    public void logAfterSavingEntity(Object entity) {
        messagingTemplate.convertAndSend(TOPIC_DATABASE_LOG, format("timestamp=%s :: %s was inserted",
            Instant.now().toString(), entity.toString()));
    }
}
