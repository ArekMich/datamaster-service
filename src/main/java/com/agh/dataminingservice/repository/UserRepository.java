package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository interface for persisting {@link User} model to the database, access and retrieving them.
 * It extends Spring Data JPA’s {@link JpaRepository} interface.
 *
 * @author Arkadiusz Michalik
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
