package com.superstore.controllers.swagger;

import com.superstore.controllers.OrderController;
import com.superstore.dto.OrderDto;
import com.superstore.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    ResponseEntity<OrderDto> createOrder(@Parameter(
            name = "request",
            description = "Тело заказа",
            required = true,
            in = ParameterIn.HEADER
    ) @RequestBody OrderController.CreateOrderRequest request);

    @Operation(
            summary = "Получение заказа по id",
            description = "Возвращает заказ по id",
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
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )
    @GetMapping("/history")
    ResponseEntity<List<OrderDto>> getUserOrderHistory();

    @Operation(
            summary = "Просмотр статуса заказа",
            description = "Возвращает статус заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )

    @GetMapping("/{orderId}/status")
    ResponseEntity<OrderDto> getOrderStatus(@PathVariable Long orderId);

    @Operation(
            summary = "Отмена существующего заказа ",
            description = "Отменяет заказ по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Не найдено")
            }
    )

    @PatchMapping("/{orderId}/cancel")
    ResponseEntity<Void> cancelOrder(@PathVariable Long orderId);
}
