package com.superstore.services;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.mapper.CategoryMapper;
import com.superstore.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryMapper categoryMapper;

    @Test
    public void testCreateCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto(null, "newCategory");

        Category category = new Category();
        category.setName("newCategory");

        Category savedCategory = new Category();
        savedCategory.setCategoryId(1L);
        savedCategory.setName("newCategory");

        when(categoryMapper.categoryDTOToCategory(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.categoryToCategoryDTO(savedCategory)).thenReturn(new CategoryDto(1L, "newCategory"));

        CategoryDto result = categoryService.createCategory(categoryDto);

        assertNotNull(result);
        assertEquals(1L, result.categoryId());
        assertEquals("newCategory", result.category());
    }

    @Test
    public void testEditCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "updatedCategory");

        Category existingCategory = new Category();
        existingCategory.setCategoryId(1L);
        existingCategory.setName("oldCategory");

        Category updatedCategoryEntity = new Category();
        updatedCategoryEntity.setCategoryId(1L);
        updatedCategoryEntity.setName("updatedCategory");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryMapper.categoryDTOToCategory(categoryDto)).thenReturn(updatedCategoryEntity);
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        when(categoryMapper.categoryToCategoryDTO(existingCategory)).thenReturn(categoryDto);

        CategoryDto result = categoryService.editCategory(1L, categoryDto);

        assertNotNull(result);
        assertEquals(1L, result.categoryId());
        assertEquals("updatedCategory", result.category());

        assertEquals("updatedCategory", existingCategory.getName());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("category");

        CategoryDto categoryDto = new CategoryDto(1L, "category");

        List<Category> categoriesList = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        when(categoryRepository.findAll()).thenReturn(categoriesList);
        when(categoryMapper.categoryToCategoryDTO(categoriesList.get(0))).thenReturn(categoryDtos.get(0));

        List<CategoryDto> allCategory = categoryService.getAllCategory();

        assertEquals(1, allCategory.size());
        assertEquals("category", allCategory.get(0).category());
    }

    @Test
    public void testFindById() throws Exception {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("category");

        CategoryDto categoryDto = new CategoryDto(1L, "category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDto);

        CategoryDto byId = categoryService.findById(1L);

        assertEquals(1L, byId.categoryId());
        assertEquals("category", byId.category());
    }

    @Test
    public void testFindByName() throws Exception {
        String categoryName = "existingCategory";
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName(categoryName);

        CategoryDto categoryDto = new CategoryDto(1L, categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.findByName(categoryName);

        assertNotNull(result);
        assertEquals(1L, result.categoryId());
        assertEquals(categoryName, result.category());
    }

    @Test
    public void testExistsByName() throws Exception {
        String categoryName = "existingCategory";

        when(categoryRepository.existsByName(categoryName)).thenReturn(true);

        boolean exists = categoryService.existsByName(categoryName);

        assertTrue(exists);

        when(categoryRepository.existsByName(categoryName)).thenReturn(false);

        exists = categoryService.existsByName(categoryName);

        assertFalse(exists);
    }

    @Test
    public void testDeleteById() throws Exception {

        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteById(1L);

        verify(categoryRepository).deleteById(1L);

        when(categoryRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteById(1L);
        });

        assertEquals("Category with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testExistsByCategoryId() throws Exception {

        when(categoryRepository.existsById(1L)).thenReturn(true);

        boolean exists = categoryService.existsByCategoryId(1L);

        assertTrue(exists);

        when(categoryRepository.existsById(1L)).thenReturn(false);

        exists = categoryService.existsByCategoryId(1L);

        assertFalse(exists);
    }
}
