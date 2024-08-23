package com.superstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long categoryId,
        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String name
) {
}
