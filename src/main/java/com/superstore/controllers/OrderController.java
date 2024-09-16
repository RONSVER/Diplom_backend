package com.superstore.controllers;

import com.superstore.controllers.swagger.OrderControllerSwagger;
import com.superstore.dto.OrderDto;
import com.superstore.entity.Order;
import com.superstore.services.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderController implements OrderControllerSwagger {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDto order = service.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto order = service.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getUserOrderHistory() {
        List<OrderDto> orders = service.getOrderHistory();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> getOrderStatus(@PathVariable Long orderId) {
        OrderDto orderDto = service.getOrderStatus(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    public record CreateOrderRequest(
            @NotNull
            String deliveryAddress,
            @NotNull
            Order.DeliveryMethod deliveryMethod
    ) {
    }

}
