package com.superstore.services;

import com.superstore.dto.FavoriteDto;
import com.superstore.dto.ProductDto;
import com.superstore.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findByUser_Name(String username);
    List<ProductDto> getUserFavoriteProducts();
    FavoriteDto addFavorite(Long productId);
    void removeFavorite(Long productId);
}
