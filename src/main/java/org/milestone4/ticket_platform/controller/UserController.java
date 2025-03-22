package org.milestone4.ticket_platform.controller;

import java.util.List;

import org.milestone4.ticket_platform.model.User;
import org.milestone4.ticket_platform.service.RoleService;
import org.milestone4.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    //////// DA FINIRE //////

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    // @GetMapping("search")
    // public String findByKeyword(@RequestParam("name") String title, Model model)
    // {
    // List<Ticket> tickets = ticketService.findByTitle(title);
    // model.addAttribute("tickets", tickets);
    // return "tickets/index";
    // }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        // Ticket ticket = ticketService.findById(id).get();
        model.addAttribute("user", userService.getById(id));
        return "users/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "users/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            model.addAttribute("roles", roleService.findAll());
            return "users/create-or-edit";
        }
        userService.create(userForm);
        redirectAttributes.addFlashAttribute("message", "A new user has been added");
        redirectAttributes.addFlashAttribute("alert", "alert-primary");
        return "redirect:/home";
    }

    @GetMapping("/edit")
    public String edit(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("roles", roleService.findAll());
        return "users/create-or-edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "users/create-or-edit";
        }
        userService.update(userForm);
        redirectAttributes.addFlashAttribute("message", "A User has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/home";
    }

    @GetMapping("/edit/status")
    public String editStatus(Model model) {
        model.addAttribute("user", userService.getCurrentUser().getIsAvailable());
        return "users/edit-status";
    }

    @PostMapping("/edit/status")
    public String updateStatus(@Valid @ModelAttribute("user") Boolean userForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "users/edit-status";
        }
        userService.getCurrentUser().setIsAvailable(userForm);
        redirectAttributes.addFlashAttribute("message", "A User has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/home";
    }
}
