package com.superstore.controllers;

import com.superstore.entity.Category;
import com.superstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category createdCategory = service.addCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        category.setCategoryId(id);
        Category createdCategory = service.addCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> category = service.getAllCategory();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Optional<Category> category = service.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
