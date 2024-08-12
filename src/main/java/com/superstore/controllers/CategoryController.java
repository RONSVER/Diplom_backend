package com.superstore.controllers;

import com.superstore.entity.Category;
import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService service;

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return service.addCategory(category);
    }

    @PutMapping("/{id}")
    public Category editCategory(@PathVariable Long id, @RequestBody Category category) {
        if (!service.findById(id).isPresent()) {
            return null;
        }

        category.setCategoryId(id);
        return service.addCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategory() {
        return service.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
