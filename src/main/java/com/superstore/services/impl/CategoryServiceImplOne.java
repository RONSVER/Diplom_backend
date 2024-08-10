package com.superstore.services.impl;

import com.superstore.entity.Category;
import com.superstore.repository.CategoryRepository;
import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImplOne implements CategoryService {

    private final CategoryRepository dao;

    @Override
    public Category addCategory(Category category) {
        return dao.save(category);
    }

    @Override
    public Category editCategory(Long categoryId, Category newCategory) {
        if (dao.existsById(categoryId)) {
            newCategory.setCategoryId(categoryId);
            return dao.save(newCategory);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public List<Category> getAllCategory() {
        return dao.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
