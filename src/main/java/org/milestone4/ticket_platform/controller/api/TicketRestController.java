package org.milestone4.ticket_platform.controller.api;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> index() {
        return ticketService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> show(@PathVariable Integer id) {
        Optional<Ticket> ticketAttempt = ticketService.findById(id);

        if (ticketAttempt.isPresent()) {
            return new ResponseEntity<Ticket>(ticketAttempt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Ticket> store(@Valid @RequestBody Ticket ticket) {
        return new ResponseEntity<Ticket>(ticketService.create(ticket), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Ticket> update(@Valid @RequestBody Ticket ticket, @PathVariable Integer id) {
        Optional<Ticket> ticketAttempt = ticketService.findById(id);

        if (ticketAttempt.isPresent()) {
            return new ResponseEntity<Ticket>(ticketService.update(ticket), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ticket> delete(@PathVariable Integer id){
        Optional<Ticket> ticketAttempt = ticketService.findById(id);

        if (ticketAttempt.isPresent()) {
            ticketService.deleteById(id);
            return new ResponseEntity<Ticket>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
