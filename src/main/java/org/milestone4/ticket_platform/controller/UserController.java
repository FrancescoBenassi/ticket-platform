package org.milestone4.ticket_platform.controller;

import java.util.List;

import org.milestone4.ticket_platform.model.User;
import org.milestone4.ticket_platform.service.TicketService;
import org.milestone4.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users/show";
    }

    @PostMapping("/edit/status")
    public String postMethodName(@RequestParam("isAvailable") Boolean IsAvailable, Model model, RedirectAttributes redirectAttributes) {
        User userCurrent = userService.getCurrentUser();
        if (ticketService.ticketCompleted(userCurrent) && !userCurrent.getIsAdmin()) {
            userCurrent.setIsAvailable(IsAvailable);
            userService.update(userCurrent);
        } else {
            redirectAttributes.addFlashAttribute("message", "A Status user current cannot be modified");
            redirectAttributes.addFlashAttribute("alert", "alert-danger");
        }
        return "redirect:/users";
    }
}