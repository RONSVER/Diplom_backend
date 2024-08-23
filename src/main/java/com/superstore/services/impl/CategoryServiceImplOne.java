package com.superstore.services.impl;

import com.superstore.entity.Category;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.exceptions.InvalidCategoryNameException;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.repository.CategoryRepository;
import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImplOne implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImplOne.class);

    private final CategoryRepository dao;

    @Override
    public Category addCategory(Category category) {
        return dao.save(category);
    }

    @Override
    public Category editCategory(Long categoryId, Category newCategory) {
        Category existingCategory = dao.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));

        if (!newCategory.getName().matches("[\\p{L}]+")) {
            logger.error("Syntax error in {}", newCategory.getCategoryId());
            throw new InvalidCategoryNameException("Syntax error in " + newCategory.getCategoryId());
        }

        existingCategory.setName(newCategory.getName());

        return dao.save(existingCategory);
    }

    @Override
    public List<Category> getAllCategory() {
        return dao.findAll();
    }

    @Override
    public Category findById(Long id) {

        return dao.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category with ID {} not found", id);
                    return new CategoryNotFoundException("Category with ID " + id + " not found");
                });
    }

    @Override
    public void deleteById(Long id) {
        if (dao.findById(id).isEmpty()) {
            logger.error("Category with ID {} not found", id);
            throw new CategoryNotFoundException("Category with ID " + id + " not found");
        }

        dao.deleteById(id);
    }

    @Override
    public boolean existsByCategoryId(Long id) {
        return dao.existsById(id);
    }
}
