package com.superstore.services.impl;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.mapper.CategoryMapper;
import com.superstore.repository.CategoryRepository;
import com.superstore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryMapper categoryMapper;

    private final CategoryRepository dao;

    @Override
    public CategoryDto findByName(String name) {
        return dao.findByName(name).map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new CategoryNotFoundException("Category with name " + name + " not found"));
    }

    @Override
    public boolean existsByName(String name) {
        return dao.existsByName(name);
    }

    private Category createAndSaveCategory(Category category) {
        return dao.save(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        Category savedCategory = createAndSaveCategory(category);
        return categoryMapper.categoryToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDto editCategory(Long categoryId, CategoryDto newCategory) {
        Category existingCategory = dao.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));

        Category updatedCategoryEntity = categoryMapper.categoryDTOToCategory(newCategory);

        existingCategory.setName(updatedCategoryEntity.getName());

        return categoryMapper.categoryToCategoryDTO(dao.save(existingCategory));
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return dao.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {

        return dao.findById(id)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> {
                    logger.error("Category with ID {} not found", id);
                    return new CategoryNotFoundException("Category with ID " + id + " not found");
                });
    }

    @Override
    public void deleteById(Long id) {
        if (!dao.existsById(id)) {
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
