package org.milestone4.ticket_platform.repository;

import org.milestone4.ticket_platform.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    
}
