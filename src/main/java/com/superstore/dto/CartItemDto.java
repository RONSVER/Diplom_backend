package com.superstore.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Integer cartItemId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {}