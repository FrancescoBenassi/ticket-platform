package org.milestone4.ticket_platform.repository;

import org.milestone4.ticket_platform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
