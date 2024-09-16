package com.superstore.controllers;

import com.superstore.dto.CartDto;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.services.CartService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService service;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;
    public static class AddProductToCartRequest {

        private long productId;
        private int quantity;

        public AddProductToCartRequest() {
        }

        public AddProductToCartRequest(long productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static class UpdateCartItemQuantityRequest {

        private long cartItemId;
        private int quantity;

        public UpdateCartItemQuantityRequest(){}

        public UpdateCartItemQuantityRequest(long cartItemId,int quantity){
            this.cartItemId = cartItemId;
            this.quantity = quantity;
        }

        public long getCartItemId(){
            return cartItemId;
        }

        public int getQuantity(){
            return quantity;
        }
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void getCart() throws Exception {
        // given
        CartDto expectedCart = new CartDto(1, 1L, Collections.emptyList(), new BigDecimal(111));
        given(service.getCart()).willReturn(expectedCart);
        
        // when & then
        mockMvc.perform(get("/v1/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{ }"));
        
        verify(service, times(1)).getCart();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void updateCartItemQuantity() throws Exception {
        // given

        UpdateCartItemQuantityRequest request = new UpdateCartItemQuantityRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        doNothing().when(service).updateCartItemQuantity(any(), any());

        // when & then
        mockMvc.perform(put("/v1/cart/update-quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(service, times(1)).updateCartItemQuantity(any(), any());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void addProductToCart() throws Exception {
        // given
        AddProductToCartRequest request = new AddProductToCartRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        CartDto expectedCart = new CartDto(1, 1L, Collections.emptyList(), new BigDecimal(111));
        given(service.addProductToCart(any(), any())).willReturn(expectedCart);

        // when & then
        mockMvc.perform(post("/v1/cart/add")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).addProductToCart(any(), any());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void removeProductFromCart() throws Exception {
        // given
        long productId = 1L;
        doNothing().when(service).removeProductFromCart(productId);

        // when & then
        mockMvc.perform(delete("/v1/cart/remove/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).removeProductFromCart(productId);
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void clearCart() throws Exception {
        // when & then
        mockMvc.perform(delete("/v1/cart/clear")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).clearCart();
    }
}
