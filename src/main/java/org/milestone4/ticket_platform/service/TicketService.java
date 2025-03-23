package org.milestone4.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Category;
import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.model.User;
import org.milestone4.ticket_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Ticket> findTicketByCategory(Category category) {
        Optional<Ticket> tickets = ticketRepository.findTicketByCategory(category);
        return tickets;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket getById(Integer id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found with id " + id);
        }
        return ticket.get();
    }

    public Optional<Ticket> findById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Boolean ticketCompleted(User user) {
        for (Ticket ticket : user.getTickets()) {
            if (!ticket.getStatus().getName().equals("Completato")) {
                return false;
            }
        }
        return true;
    }

    public Ticket create(Ticket ticket) {
        ticket.getUser().setIsAvailable(false);
        return ticketRepository.save(ticket);
    }

    public Ticket update(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

}
