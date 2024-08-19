package com.superstore.controllers;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.mapper.CategoryMapper;

import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        return categoryMapper.categoryToCategoryDTO(service.addCategory(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public CategoryDto editCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        if (!service.findById(id).isPresent()) {
            return null;
        }

        Category byId = service.findById(id).get();
        byId.setName(categoryDto.name());
        return categoryMapper.categoryToCategoryDTO(service.addCategory(byId));
    }

    @GetMapping
    public List<CategoryDto> getAllCategory() {
        return service.getAllCategory()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList()
                );
    }


    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {

        return categoryMapper.categoryToCategoryDTO(service.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No category with id " + id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
