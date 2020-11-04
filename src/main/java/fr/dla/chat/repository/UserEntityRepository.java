package fr.dla.chat.repository;


import fr.dla.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for the User entity.
 */
@Repository
public interface UserEntityRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}
