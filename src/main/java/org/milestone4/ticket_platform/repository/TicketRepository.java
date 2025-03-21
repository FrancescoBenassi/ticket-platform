package org.milestone4.ticket_platform.repository;

import java.util.List;

import org.milestone4.ticket_platform.model.Category;
import org.milestone4.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByTitleContainingIgnoreCase(String title);
    public List<Ticket> findByCategory(Category category);
    public List<Ticket> findByUserId(Integer user);
}
