package com.superstore.dto;


public record OrderItemDto(
        Integer productId,
        Integer quantity,
        Double price
) {}