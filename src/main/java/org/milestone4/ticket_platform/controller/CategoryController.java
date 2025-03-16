package org.milestone4.ticket_platform.controller;

import java.util.List;

import org.milestone4.ticket_platform.model.Category;
import org.milestone4.ticket_platform.service.CategoryService;
import org.milestone4.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    @GetMapping("search")
    public String findByKeyword(@RequestParam("name") String name, Model model) {
        List<Category> categories = categoryService.findByName(name);
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        // Ticket ticket = ticketService.findById(id).get();
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("tickets", ticketService.findAll());
        return "categories/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("category", new Category());
        model.addAttribute("tickets", ticketService.isAvailableTicket(ticketService.findAll()));
        return "categories/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            model.addAttribute("tickets", ticketService.isAvailableTicket(ticketService.findAll()));
            return "categories/create-or-edit";
        }

        categoryService.create(categoryForm);
        redirectAttributes.addFlashAttribute("message", "A new category has been added");
        redirectAttributes.addFlashAttribute("alert", "alert-primary");
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("tickets", ticketService.isAvailableTicket(ticketService.findAll()));
        return "categories/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tickets", ticketService.isAvailableTicket(ticketService.findAll()));
            return "categories/create-or-edit";
        }

        categoryService.update(categoryForm);
        redirectAttributes.addFlashAttribute("message", "A Category has been updated");
        redirectAttributes.addFlashAttribute("alert", "alert-success");
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        Category category = categoryService.getById(id);

        categoryService.delete(category);

        redirectAttributes.addFlashAttribute("message", "A Category has been deleted");
        redirectAttributes.addFlashAttribute("alert", "alert-danger");
        return "redirect:/categories";
    }

}
