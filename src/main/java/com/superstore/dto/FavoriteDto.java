package com.superstore.dto;

public record FavoriteDto(
        Long favoriteId,
        Long userId,
        Long productId
) {}