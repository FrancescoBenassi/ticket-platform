package org.milestone4.ticket_platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.model.User;
import org.milestone4.ticket_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        if(user.isIsAdmin()){
           user.setIsAvailable(false);
        }
        user.setIsAvailable(true);
        return userRepository.save(user);
    }

    public User update(User user) {
        if(user.isIsAdmin()){
            user.setIsAvailable(false);
         }
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with id " + id);
        }
        return user.get();
    }

    public List<User> isAvailableOperator(List<User> users) {
        List<User> userAttempt = new ArrayList<User>();
        for (User user : users) {
                if (!user.isIsAdmin()) {
                    userAttempt.add(user);
                }
        }
        return userAttempt;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found, with username " + username));
    }
}