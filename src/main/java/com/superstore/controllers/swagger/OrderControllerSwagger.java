package com.superstore.controllers.swagger;

import com.superstore.dto.OrderDto;
import com.superstore.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrderControllerSwagger {
    @PostMapping
    @Operation(
            summary = "Оформление заказа",
            description = "Создаёт новый заказ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Создан заказ"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request);
    @Operation(
            summary = "Просмотр статуса заказа",
            description = "Возвращает статус заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )

    @GetMapping("/{orderId}")
    ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId);
    @Operation(
            summary = "История покупок пользователя",
            description = "Показывает историю покупок пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/history")
    ResponseEntity<List<OrderDto>> getUserOrderHistory();

    @GetMapping("/{orderId}/status")
    ResponseEntity<OrderDto> getOrderStatus(@PathVariable Long orderId);

    @PatchMapping("/{orderId}/cancel")
    ResponseEntity<Void> cancelOrder(@PathVariable Long orderId);

    public record CreateOrderRequest(
            @NotNull
            String deliveryAddress,
            @NotNull
            Order.DeliveryMethod deliveryMethod
    ) {
    }
}
