package com.superstore.controllers.swagger;

import com.superstore.dto.FavoriteDto;
import com.superstore.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface FavoriteControllerSwagger {

    @GetMapping
    @Operation(
            summary = "Получение списка избранных товаров пользователя",
            description = "Показывает списка избранных товаров пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )
    ResponseEntity<List<ProductDto>> getUserFavorites();

    @PostMapping("/{productId}")

    @Operation(
            summary = "Добавление товара в избранное",
            description = "Добавляет товар в избранное",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )
    ResponseEntity<FavoriteDto> addFavorite(@PathVariable Long productId);

    @DeleteMapping("/{productId}")
    @Operation(
            summary = "Удаление товара из избранного",
            description = "Удаляет товар из избранного",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )
    ResponseEntity<Void> removeFavorite(@PathVariable Long productId);
}
