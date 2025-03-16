package org.milestone4.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.milestone4.ticket_platform.model.Category;
import org.milestone4.ticket_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByTitle(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public Category getById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found with id " + id);
        }
        return category.get();
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

     public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
