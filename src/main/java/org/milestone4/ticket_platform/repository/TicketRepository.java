package org.milestone4.ticket_platform.repository;

import org.milestone4.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    
}
