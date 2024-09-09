package com.superstore.dto;

import com.superstore.entity.Order;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        Integer orderId,
        Long userId,
        LocalDateTime createdAt,
        String deliveryAddress,
        Order.DeliveryMethod deliveryMethod,
        Order.Status status,
        List<OrderItemDto> items
) {}
