package com.superstore.controllers;

import com.superstore.controllers.swagger.CartControllerSwagger;
import com.superstore.dto.CartDto;
import com.superstore.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



//    TODO: возможно нужно переосмыслить пару методов



@RestController
@RequestMapping("/v1/cart")
@AllArgsConstructor
public class CartController implements CartControllerSwagger {



    private CartService service;

    @GetMapping
    @Override
    public ResponseEntity<CartDto> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<CartDto> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartDto cartDto = service.addProductToCart(addProductToCartRequest.getProductId(), addProductToCartRequest.getQuantity());
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/remove/{productId}")
    @Override
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        service.removeProductFromCart(productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    @Override
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-quantity")
    @Override
    public ResponseEntity<Void> updateCartItemQuantity(@RequestBody UpdateCartItemQuantityRequest request) {
        service.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

}
