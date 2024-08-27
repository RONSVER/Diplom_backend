package com.superstore.controllers.swagger;

import com.superstore.dto.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryControllerSwagger {
    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto);

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto);

    @GetMapping
    ResponseEntity<List<CategoryDto>> getAllCategory();

    @GetMapping("/{id}")
    ResponseEntity<CategoryDto> findById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<Void> deleteById(@PathVariable Long id);
}
