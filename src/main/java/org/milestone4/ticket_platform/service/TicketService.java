package org.milestone4.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.repository.NoteRepository;
import org.milestone4.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NoteRepository noteRepository;

    public List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitleContainingIgnoreCase(title);
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

    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket update(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /////// DA FINIRE ////////

    // public void delete( Ticket ticket){
    // for (Note note : ticket.getNotes()) {
    // noteRepository.delete(note);
    // }
    // ticketRepository.delete(ticket);
    // }

    public void delete(Ticket ticket){
        ticketRepository.delete(ticket);
    }

    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

}
