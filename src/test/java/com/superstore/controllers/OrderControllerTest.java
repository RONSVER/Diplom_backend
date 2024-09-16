package com.superstore.controllers;

import com.superstore.dto.OrderDto;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void shouldCreateOrder() throws Exception {
        String requestBody = "{ \"deliveryAddress\": \"123 Sunny Street, Happy Town\", \"deliveryMethod\": \"COURIER\" }";
        OrderDto orderDto = mock(OrderDto.class);
        when(orderService.createOrder(ArgumentMatchers.any())).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void shouldGetOrder() throws Exception {
        OrderDto orderDto = mock(OrderDto.class);
        Long orderId = 5L;
        when(orderService.getOrderById(orderId)).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void shouldGetUserOrderHistory() throws Exception {
        List<OrderDto> orderDtos = Arrays.asList(mock(OrderDto.class));
        when(orderService.getOrderHistory()).thenReturn(orderDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void shouldGetOrderStatus() throws Exception {
        OrderDto orderDto = mock(OrderDto.class);
        Long orderId = 6L;
        when(orderService.getOrderStatus(orderId)).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/" + orderId + "/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
    void shouldCancelOrder() throws Exception {
        Long orderId = 7L;
        doNothing().when(orderService).cancelOrder(orderId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/orders/" + orderId + "/cancel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
