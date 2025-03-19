package org.milestone4.ticket_platform.repository;

import java.util.Optional;

import org.milestone4.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}
