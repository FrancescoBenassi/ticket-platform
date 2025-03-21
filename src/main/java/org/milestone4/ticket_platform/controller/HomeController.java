package org.milestone4.ticket_platform.controller;

import org.milestone4.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
            model.addAttribute("user", userService.getCurrentUser());
            // model.addAttribute("isAvailable", userService.getCurrentUser().getIsAvailable());
            // model.addAttribute("tickets", ticketService.getTicketsAssignedToUser(userService.getCurrentUser()));
        return "home/index";
    }
}