package com.superstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        // make schema for swagger (For example: @Schema(description = "description text", example = "1")
        @Schema(description = "ID of the category", example = "1")
        Long categoryId,
        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String category
) {
}
