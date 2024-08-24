package com.superstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(
        // make schema for swagger (For example: @Schema(description = "description text", example = "1")
        Long productId,

        String name,

        String description,

        BigDecimal price,

        BigDecimal discountPrice,

        String imageURL
) {
}
