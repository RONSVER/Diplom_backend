package com.superstore.controllers;

import com.superstore.dto.CartDto;
import com.superstore.services.CartService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



//    TODO: возможно нужно переосмыслить пару методов



@RestController
@RequestMapping("/v1/cart")
@AllArgsConstructor
public class CartController {



    private CartService service;

    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartDto cartDto = service.addProductToCart(addProductToCartRequest.getProductId(), addProductToCartRequest.getQuantity());
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        service.removeProductFromCart(productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<Void> updateCartItemQuantity(@RequestBody UpdateCartItemQuantityRequest request) {
        service.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

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
