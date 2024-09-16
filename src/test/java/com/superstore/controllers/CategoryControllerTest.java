package com.superstore.controllers;

import com.superstore.dto.CategoryDto;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator"})
    void testSaveCategory() throws Exception {
        CategoryDto newCategory = new CategoryDto(1L, "Electronics");
        given(categoryService.createCategory(newCategory)).willReturn(newCategory);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
                .content("{\"categoryId\":\"1\",\"category\":\"Electronics\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator"})
    void testUpdateCategory() throws Exception {
        CategoryDto categoryUpdate = new CategoryDto(1L, "Updated Electronics");
        given(categoryService.editCategory(categoryUpdate.categoryId(), categoryUpdate)).willReturn(categoryUpdate);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/categories/" + categoryUpdate.categoryId())
                .content("{\"categoryId\":\"1\",\"category\":\"Updated Electronics\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void testGetAllCategory() throws Exception {
        List<CategoryDto> categories = Arrays.asList(new CategoryDto(1L, "Electronics"), new CategoryDto(2L, "Computers"));
        given(categoryService.getAllCategory()).willReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/categories").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void testFindById() throws Exception {
        Long id = 1L;
        CategoryDto category = new CategoryDto(id, "Electronics");
        given(categoryService.findById(id)).willReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/categories/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void testDeleteById() throws Exception {
        Long id = 1L;
        doNothing().when(categoryService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/categories/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
