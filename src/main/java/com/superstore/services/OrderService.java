package com.superstore.services;

import com.superstore.controllers.OrderController;
import com.superstore.dto.OrderDto;
import com.superstore.entity.Order;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderController.CreateOrderRequest request);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrderHistory();
    OrderDto getOrderStatus(Long orderId);
    void cancelOrder(Long orderId);
    void updateOrderStatus(Long orderId, Order.Status status);
}
