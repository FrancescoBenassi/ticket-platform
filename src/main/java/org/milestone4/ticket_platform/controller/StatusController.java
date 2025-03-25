package org.milestone4.ticket_platform.controller;

import java.util.List;

import org.milestone4.ticket_platform.model.Status;
import org.milestone4.ticket_platform.service.StatusService;
import org.milestone4.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public String index(Model model) {
        List<Status> status = statusService.findAll();
        model.addAttribute("status", status);
        return "status/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("status", statusService.getById(id));
        model.addAttribute("tickets", ticketService.findAll());
        return "status/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("status", new Status());
        return "status/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("status") Status statusForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            return "status/create-or-edit";
        }

        statusService.create(statusForm);
        redirectAttributes.addFlashAttribute("message", "A new Status has been added");
        redirectAttributes.addFlashAttribute("alert", "alert-primary");
        return "redirect:/status";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        if (statusService.checkNameCompletato(id)) {
            redirectAttributes.addFlashAttribute("message", "The Status 'Completato' cannot be updated");
            redirectAttributes.addFlashAttribute("alert", "alert-danger");
            return "redirect:/status";
        }
        model.addAttribute("status", statusService.getById(id));
        return "status/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("status") Status statusForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "status/create-or-edit";
        }

        statusService.update(statusForm);
        redirectAttributes.addFlashAttribute("message", "A Status has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/status";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (statusService.checkNameCompletato(id)) {
            redirectAttributes.addFlashAttribute("message", "The Status 'Completato' cannot be deleted");
            redirectAttributes.addFlashAttribute("alert", "alert-danger");
            return "redirect:/status";
        }
        Status status = statusService.getById(id);
        statusService.delete(status);
        redirectAttributes.addFlashAttribute("message", "A Status has been deleted");
        redirectAttributes.addFlashAttribute("alert", "alert-danger");
        return "redirect:/status";
    }

}
