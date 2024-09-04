package com.superstore.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Integer cartId,
        Long userId,
        List<CartItemDto> items,
        BigDecimal total
) {}