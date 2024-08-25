package com.superstore.services;

import com.superstore.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category addCategory(Category category);

    Category editCategory(Long categoryId, Category category);

    List<Category> getAllCategory();

    Optional<Category> findById(Long id);

    void deleteById(Long id);

    Category findByName(String name);

    boolean existsByName(String name);
}
