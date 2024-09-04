package com.superstore.services.impl;

import com.superstore.dto.CartDto;
import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.entity.CartItem;
import com.superstore.entity.Product;
import com.superstore.entity.User;
import com.superstore.exceptions.CartItemNotFoundException;
import com.superstore.exceptions.CartNotFoundException;
import com.superstore.exceptions.ProductNotFoundException;
import com.superstore.mapper.CartItemMapper;
import com.superstore.repository.CartItemRepository;
import com.superstore.repository.CartRepository;
import com.superstore.repository.ProductRepository;
import com.superstore.services.CartService;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository dao;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartItemMapper cartItemMapper;

    private Cart createCartForUser() {
        long userId = userService.getCurrentUserId();
        Cart cart = new Cart();
        User user = new User();
        user.setUserId(userId);
        cart.setUser(user);
        return dao.save(cart);
    }

    @Override
    public CartDto getCart() {

        long userId = userService.getCurrentUserId();

        Cart cart = dao.findByUserUserId(userService.getCurrentUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user id " + userId));

        List<CartItem> items = cartItemRepository.findByCartCartId(cart.getCartId());

        BigDecimal total = items.stream()
                .map(item -> {
                    BigDecimal price = item.getProduct().getDiscountPrice() != null ?  item.getProduct().getDiscountPrice() : item.getProduct().getPrice();
                    return price.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartItemDto> cartItemDtos = items.stream()
                .map(cartItemMapper::cartItemToCartItemDto)
                .toList();

        return new CartDto(cart.getCartId(), cart.getUser().getUserId(), cartItemDtos, total);
    }

    @Transactional
    public CartDto addProductToCart(Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        long userId = userService.getCurrentUserId();
        Cart cart = dao.findByUserUserId(userId)
                .orElseGet(this::createCartForUser);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        CartItem cartItem = cartItemRepository.findByCartCartId(cart.getCartId())
                .stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(0)
                            .build());

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);

        return getCart();
    }

    @Transactional
    public void removeProductFromCart(Long productId) {
        long userId = userService.getCurrentUserId();
        Cart cart = dao.findByUserUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user id " + userId));

        CartItem cartItem = cartItemRepository.findByCartCartId(cart.getCartId())
                .stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found for product id " + productId));

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart() {
        long userId = userService.getCurrentUserId();
        Cart cart = dao.findByUserUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user id " + userId));
        cartItemRepository.deleteAll(cart.getCartItems());
    }
    @Override
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id " + cartItemId));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }
}
