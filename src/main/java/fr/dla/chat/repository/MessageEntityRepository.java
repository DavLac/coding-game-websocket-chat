package fr.dla.chat.repository;


import fr.dla.chat.domain.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Message entity.
 */
@Repository
public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {

}
