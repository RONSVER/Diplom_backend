package com.superstore.services.impl;

import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.entity.CartItem;
import com.superstore.entity.User;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.mapper.CartItemMapper;
import com.superstore.mapper.UserMapper;
import com.superstore.repository.CartItemRepository;
import com.superstore.repository.CartRepository;
import com.superstore.repository.ProductRepository;
import com.superstore.repository.UserRepository;
import com.superstore.services.CartService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private CartRepository dao;

    private CartItemRepository cartItemRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private CartItemMapper cartItemMapper;

    private UserMapper userMapper;

    private final static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public Cart getCart(Long userId, CartItemDto cartItemDto) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        Optional<User> user = userRepository.findById(userId);

        // проверка на продукты и получаение продукта

        Cart finalCart;

        if (dao.existsByUser_UserId(userId)) {
            finalCart = dao.findByUser_UserId(userId).get();
        } else {
            Cart cart = new Cart();
            cart.setUser(userMapper.userDTOToUser(userMapper.userToUserDTO(user.get())));
            finalCart = saveCart(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(finalCart);
//        cartItem.setProduct(product); получение продукта
        cartItem.setQuantity(cartItemDto.quantity());
        addCartItem(cartItemMapper.cartItemToCartItemDto(cartItem));

        return finalCart;
    }

    @Override
    public Cart saveCart(Cart cart) {
        return dao.save(cart);
    }

    public CartItem createAndSaveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemMapper.cartItemDtoToCartItem(cartItemDto);
        CartItem saveCartItem = createAndSaveCartItem(cartItem);

        return cartItemMapper.cartItemToCartItemDto(saveCartItem);
    }
}
