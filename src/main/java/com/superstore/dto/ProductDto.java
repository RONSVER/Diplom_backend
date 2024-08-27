package com.superstore.dto;

import com.superstore.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDto(
        // make schema for swagger (For example: @Schema(description = "description text", example = "1")
        Long productId,

        @Schema(description = "Название продукта", type = "String", example = "Лопата")
        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String name,

        @Schema(description = "Описание продукта", type = "String", example = "Ручной шанцевый инструмент, используемый для работы с грунтом и сыпучими веществами.")
        @NotBlank(message = "Description is required")
        @Size(min = 4, message = "Description must be at least 4 characters long")
        String description,

        @Schema(description = "Цена продукта", type = "BigDecimal", example = "10,500")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @Schema(description = "Категория продукта", type = "String", example = "Tools and equipment (Инструменты и оборудование)")
        @Size(min = 4, message = "Category must be at least 4 characters long")
        String category,

        @Schema(description = "Цена со скидкой", type = "BigDecimal", example = "7,500")
        @DecimalMin(value = "0.0", inclusive = false, message = "Discount price must be greater than zero")
        @DecimalMax(value = "1000000.0", message = "Discount price must be less than 1,000,000")
        BigDecimal discountPrice,

        @Schema(description = "URL-адрес изображения", type = "String", example = "https://api.example.com/v1/.../image/{image}")
        @NotBlank(message = "Image URL is mandatory")
        @Size(max = 255, message = "Image URL must be less than 255 characters long")
        String imageURL
) {
}