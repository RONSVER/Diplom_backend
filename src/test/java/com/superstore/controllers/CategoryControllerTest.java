package com.superstore.controllers;

import com.superstore.dto.CategoryDto;
import com.superstore.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })
    public void testSaveCategory_Success() throws Exception{
        CategoryDto categoryDto = new CategoryDto(1L, "newCategory");

        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": \"newCategory\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })
    public void testUpdateCategory() throws Exception {
        CategoryDto category = new CategoryDto(1L, "category");

        when(categoryService.editCategory(eq(1L), any(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(put("/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": \"updatedCategory\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("category"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })
    public void testGetAllCategory() throws Exception {
        // Arrange
        List<CategoryDto> updatedCategory = List.of(new CategoryDto(1L, "updatedCategory"));

        // Мокируем поведение сервиса
        when(categoryService.getAllCategory()).thenReturn(updatedCategory);

        // Act & Assert
        mockMvc.perform(get("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId").value(1L))
                .andExpect(jsonPath("$[0].category").value("updatedCategory"));
    }

    @Test
    @WithMockUser(username = "Client", authorities = { "Administrator", "Client" })
    public void testFindById() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "Category");

        when(categoryService.findById(1L)).thenReturn(categoryDto);

        mockMvc.perform(get("/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Category"));
    }

    @Test
    @WithMockUser(username = "Client", authorities = { "Administrator", "Client" })
    public void testDeleteById() throws Exception {

        mockMvc.perform(delete("/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteById(1L);
    }
}