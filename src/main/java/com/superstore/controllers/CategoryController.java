package com.superstore.controllers;

import com.superstore.controllers.swagger.CategoryControllerSwagger;
import com.superstore.dto.CategoryDto;
import com.superstore.mapper.CategoryMapper;
import com.superstore.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@AllArgsConstructor
public class CategoryController implements CategoryControllerSwagger {

    private CategoryService service;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(categoryDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = service.editCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }


    @GetMapping
    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return ResponseEntity.ok(service.getAllCategory());
    }


    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
