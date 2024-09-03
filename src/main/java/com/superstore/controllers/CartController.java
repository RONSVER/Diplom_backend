package com.superstore.controllers;

import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cart")
@AllArgsConstructor
public class CartController {

    private CartService service;

    @PostMapping
    public ResponseEntity<Cart> addProductToCart(@RequestParam Long userId, @RequestParam CartItemDto cartItemDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.getCart(userId, cartItemDto));
    }
}
