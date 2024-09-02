package com.superstore.services.impl;

import com.superstore.dto.FavoriteDto;
import com.superstore.entity.Favorite;
import com.superstore.entity.Product;
import com.superstore.entity.User;
import com.superstore.exceptions.ProductNotFoundException;
import com.superstore.mapper.ProductMapper;
import com.superstore.mapper.UserMapper;
import com.superstore.repository.FavoriteRepository;
import com.superstore.services.FavoriteService;
import com.superstore.services.ProductService;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);


    private final FavoriteRepository dao;
    private final UserService userService;
    private final ProductService productService;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    @Override
    public List<Favorite> findByUser_Name(String username) {
        return dao.findByUser_Name(username);
    }

    @Override
    public FavoriteDto addFavorite(Long productId) {
        if (!productService.existsById(productId)) {
            logger.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        Long currentUserId = userService.getCurrentUserId();

        if (dao.existsByUser_UserIdAndProduct_ProductId(currentUserId, productId)) {
            throw new IllegalStateException("Product is already in favorites");
        }

        User user = userMapper.userDTOToUser(userService.findById(currentUserId));
        Product product = productMapper.productDtoToProduct(productService.getProductById(productId));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        dao.save(favorite);
        return null;
    }

    @Transactional
    @Override
    public void removeFavorite(Long productId) {
        Long currentUserId = userService.getCurrentUserId();
        if (!productService.existsById(productId)) {
            logger.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
        dao.deleteByUser_UserIdAndProduct_ProductId(currentUserId, productId);
    }
}
