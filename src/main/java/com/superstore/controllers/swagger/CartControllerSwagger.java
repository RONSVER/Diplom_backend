package com.superstore.controllers.swagger;

import com.superstore.dto.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CartControllerSwagger {
    @Operation(
            summary = "Получение информации о корзине",
            description = "Выводит информацию о корзине",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок")
            }
    )

    @GetMapping
    ResponseEntity<CartDto> getCart();

    @Operation(
            summary = "Добавление товара в корзину",
            description = "Добавляет товар в корзину",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Неверный запрос")
            }
    )
    @PostMapping("/add")
    ResponseEntity<CartDto> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest);

    @Operation(
            summary = "Удаление товара из корзины по идентификатору",
            description = "Удаляет товар из корзины по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )

    @DeleteMapping("/remove/{productId}")
    ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId);

    @Operation(
            summary = "Очистка корзины",
            description = "Очистщает корзину",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )

    @DeleteMapping("/clear")
    ResponseEntity<Void> clearCart();

    @Operation(
            summary = "Изменение количества товара в корзине",
            description = "Измененяет количество товара в корзине",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ок")
            }
    )

    @PutMapping("/update-quantity")
    ResponseEntity<Void> updateCartItemQuantity(@RequestBody UpdateCartItemQuantityRequest request);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    public static class AddProductToCartRequest {
        private Long productId;
        private Integer quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static public class UpdateCartItemQuantityRequest {
        private Long cartItemId;
        private Integer quantity;
    }
}
