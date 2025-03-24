package org.milestone4.ticket_platform.controller.api;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/category/{name}")
     public List<Ticket> findTicketsByCategory(@PathVariable String nameCategory){
         return ticketService.findByCategory(nameCategory);
     }
 
     @GetMapping("/status/{statusId}")
     public List<Ticket> filteredByStatus(@PathVariable Integer statusId){
         return ticketService.findByStatus(statusId);
     }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> show(@PathVariable Integer id) {
        Optional<Ticket> ticketAttempt = ticketService.findById(id);

        if (ticketAttempt.isPresent()) {
            return new ResponseEntity<Ticket>(ticketAttempt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
