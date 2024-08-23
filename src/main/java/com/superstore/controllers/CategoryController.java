package com.superstore.controllers;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import com.superstore.mapper.CategoryMapper;

import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService service;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToCategoryDTO(service.addCategory(category)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        category.setCategoryId(id);

        Category updatedCategory = service.editCategory(id, category);

        CategoryDto updatedCategoryDto = categoryMapper.categoryToCategoryDTO(updatedCategory);

        return ResponseEntity.ok(updatedCategoryDto);
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> collects = service.getAllCategory()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList()
                );

        return ResponseEntity.ok(collects);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok(categoryMapper.categoryToCategoryDTO(service.findById(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.existsByCategoryId(id)) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
