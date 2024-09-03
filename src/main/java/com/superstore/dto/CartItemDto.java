package com.superstore.dto;

public record CartItemDto(
        Long productId,
        Integer quantity
) {
}
