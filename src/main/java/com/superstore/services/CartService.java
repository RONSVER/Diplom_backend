package com.superstore.services;

import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.entity.CartItem;

public interface CartService {

    Cart getCart(Long userId, CartItemDto cartItemDto);

    Cart saveCart(Cart cart);

    CartItemDto addCartItem(CartItemDto cartItem);
}
