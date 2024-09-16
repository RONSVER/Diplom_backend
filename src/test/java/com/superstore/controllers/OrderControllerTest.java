package com.superstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superstore.dto.OrderDto;
import com.superstore.entity.Order;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.services.OrderService;
import org.hamcrest.Matchers;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(OrderController.class)
@WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
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

    void shouldCreateOrder() throws Exception {
        OrderController.CreateOrderRequest requestBody = new OrderController.CreateOrderRequest(
                "123 Sunny Street, Happy Town",
                Order.DeliveryMethod.COURIER
        );
        OrderDto expectedOrderDto = new OrderDto(1, 1L, LocalDate.of(2024, Month.JANUARY, 18).atStartOfDay(), "123 Sunny Street, Happy Town", Order.DeliveryMethod.COURIER, Order.Status.PROCESSING, Collections.emptyList()); // Assuming the OrderDto class looks something similar...
        when(orderService.createOrder(ArgumentMatchers.any())).thenReturn(expectedOrderDto);
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/v1/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(requestBody)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.deliveryAddress", Matchers.is("123 Sunny Street, Happy Town")))
                    .andExpect(jsonPath("$.deliveryMethod", Matchers.is("COURIER")));

            verify(orderService, times(1)).createOrder(ArgumentMatchers.any(OrderController.CreateOrderRequest.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetOrder() throws Exception {
        OrderDto orderDto = mock(OrderDto.class);
        Long orderId = 5L;
        when(orderService.getOrderById(orderId)).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void shouldGetUserOrderHistory() throws Exception {
        List<OrderDto> orderDtos = Arrays.asList(mock(OrderDto.class));
        when(orderService.getOrderHistory()).thenReturn(orderDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetOrderStatus() throws Exception {
        OrderDto orderDto = mock(OrderDto.class);
        Long orderId = 6L;
        when(orderService.getOrderStatus(orderId)).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/" + orderId + "/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void shouldCancelOrder() throws Exception {
        Long orderId = 7L;
        doNothing().when(orderService).cancelOrder(orderId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/orders/" + orderId + "/cancel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
