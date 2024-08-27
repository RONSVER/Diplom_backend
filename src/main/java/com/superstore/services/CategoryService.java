package com.superstore.services;

import com.superstore.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto category);

    CategoryDto editCategory(Long categoryId, CategoryDto category);

    List<CategoryDto> getAllCategory();

    CategoryDto findById(Long id);

    void deleteById(Long id);

    CategoryDto findByName(String name);

    boolean existsByName(String name);

    boolean existsByCategoryId(Long id);
}
