package org.milestone4.ticket_platform.controller;

import java.time.LocalDate;
import java.util.List;

import org.milestone4.ticket_platform.model.Note;
import org.milestone4.ticket_platform.model.Ticket;
import org.milestone4.ticket_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    //////// DA FINIRE //////

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public String index(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("search")
    public String findByKeyword(@RequestParam("name") String title, Model model) {
        List<Ticket> tickets = ticketService.findByTitle(title);
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        // Ticket ticket = ticketService.findById(id).get();
        model.addAttribute("ticket", ticketService.getById(id));
        return "tickets/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("notes", new Note());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("status", statusService.findAll());
        model.addAttribute("users", userService.isAvailableOperator(userService.findAll()));
        return "tickets/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("status", statusService.findAll());
            model.addAttribute("users", userService.isAvailableOperator(userService.findAll()));
            return "tickets/create-or-edit";
        }
        ticketForm.getUser().setIsAvailable(false);
        ticketService.create(ticketForm);
        redirectAttributes.addFlashAttribute("message", "A new ticket has been added");
        redirectAttributes.addFlashAttribute("alert", "alert-primary");
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getById(id);
        model.addAttribute("ticket", ticket);
        if (userService.getCurrentUser().isIsAdmin()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("status", statusService.findAll());
            model.addAttribute("users", userService.isAvailableOperator(userService.findAll()));
        }
        model.addAttribute("categories", ticket.getCategory());
        model.addAttribute("users", ticket.getUser());
        model.addAttribute("ticket", ticketService.getById(id));
        model.addAttribute("status", statusService.findAll());
        return "tickets/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("status", statusService.findAll());
            model.addAttribute("users", userService.isAvailableOperator(userService.findAll()));
            return "tickets/create-or-edit";
        }
        ticketService.update(ticketForm);
        redirectAttributes.addFlashAttribute("message", "A Ticket has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        Ticket ticket = ticketService.getById(id);

        ticketService.delete(ticket);

        redirectAttributes.addFlashAttribute("message", "A Ticket has been deleted");
        redirectAttributes.addFlashAttribute("alert", "alert-danger");
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/note")
    public String note(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getById(id);
        Note note = new Note();
        note.setTicket(ticket);
        note.setUser(ticketService.getByUser(id));
        note.setCreationDate(LocalDate.now());
        note.setUpdatedDate(LocalDate.now());
        model.addAttribute("note", note);
        model.addAttribute("create", true);
        return "notes/create-or-edit";
    }
}
