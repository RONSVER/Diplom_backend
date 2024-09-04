package com.superstore.services;

import com.superstore.dto.CartDto;
import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.entity.CartItem;

public interface CartService {

    CartDto getCart();
    CartDto addProductToCart(Long productId, Integer quantity);
    void removeProductFromCart(Long productId);
    void clearCart();
    void updateCartItemQuantity(Long cartItemId, Integer quantity);
}
