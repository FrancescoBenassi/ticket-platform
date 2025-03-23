package org.milestone4.ticket_platform.controller;

import org.milestone4.ticket_platform.model.Note;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("note", noteService.getById(id));
        return "notes/show";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            return "notes/create-or-edit";
        }
        noteService.create(noteForm);
        redirectAttributes.addFlashAttribute("message", "A new note has been created");
        redirectAttributes.addFlashAttribute("alert", "alert-primary");
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("note", noteService.getById(id));
        return "notes/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("note") Note noteForm,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "notes/create-or-edit";
        }
        noteService.update(noteForm);
        redirectAttributes.addFlashAttribute("message", "A new note has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        noteService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "A note has been deleted");
        redirectAttributes.addFlashAttribute("alert", "alert-danger");
        return "redirect:/tickets";
    }

}
