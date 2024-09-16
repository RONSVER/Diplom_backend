package com.superstore.controllers;

import com.superstore.dto.FavoriteDto;
import com.superstore.dto.ProductDto;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.services.FavoriteService;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(FavoriteController.class)
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void getUserFavorites() throws Exception {
        ProductDto productDto = new ProductDto(1l, "Lopata", "This is lopata", new BigDecimal(111), "Saraj", new BigDecimal(111), "http:url");
        when(favoriteService.getUserFavoriteProducts()).thenReturn(Collections.singletonList(productDto));

        mockMvc.perform(get("/v1/favorites")
                .with(user("user").roles("Client"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        InOrder inOrder = inOrder(favoriteService);
        inOrder.verify(favoriteService).getUserFavoriteProducts();
        inOrder.verifyNoMoreInteractions();

        verifyNoMoreInteractions(favoriteService);
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void addFavorite() throws Exception {
        Long productId = 1L;
        FavoriteDto favoriteDto = new FavoriteDto(1L,1L,1L);
        when(favoriteService.addFavorite(productId)).thenReturn(favoriteDto);

        mockMvc.perform(post("/v1/favorites/" + productId)
                .with(user("user").roles("Client"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        InOrder inOrder = inOrder(favoriteService);
        inOrder.verify(favoriteService).addFavorite(productId);
        inOrder.verifyNoMoreInteractions();

        verifyNoMoreInteractions(favoriteService);
    }

 @Test
 @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
 void removeFavorite() throws Exception {
     Long productId = 1L;
     doNothing().when(favoriteService).removeFavorite(productId);

     mockMvc.perform(delete("/v1/favorites/" + productId)
             .with(user("user").roles("Client"))
             .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isNoContent());

     InOrder inOrder = inOrder(favoriteService);
     inOrder.verify(favoriteService).removeFavorite(productId);
     inOrder.verifyNoMoreInteractions();

     verifyNoMoreInteractions(favoriteService);
 }
}
