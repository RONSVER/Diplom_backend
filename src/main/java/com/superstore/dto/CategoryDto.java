package com.superstore.dto;

public record CategoryDto(
        // make schema for swagger (For example: @Schema(description = "description text", example = "1")
        Long categoryId,
        String name
) {
}
