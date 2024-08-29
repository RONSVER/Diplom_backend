package com.superstore.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDto(
        // make schema for swagger (For example: @Schema(description = "description text", example = "1")
        Long productId,

        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String name,

        @NotBlank(message = "Description is required")
        @Size(min = 4, message = "Description must be at least 4 characters long")
        String description,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @Size(min = 4, message = "Category must be at least 4 characters long")
        String category,

        @DecimalMin(value = "0.0", inclusive = false, message = "Discount price must be greater than zero")
        @DecimalMax(value = "1000000.0", message = "Discount price must be less than 1,000,000")
        BigDecimal discountPrice,

        @NotBlank(message = "Image URL is mandatory")
        @Size(max = 255, message = "Image URL must be less than 255 characters long")
        String imageURL
) {
}