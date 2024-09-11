package com.superstore.services;

import com.superstore.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto editProduct(Long productId, ProductDto productDto);

    ProductDto getProductById(Long productId);

    List<ProductDto> getProducts(BigDecimal minPrice, BigDecimal maxPrice, Boolean hasDiscount, Long categoryId, String sortBy, String order);

    boolean existsById(Long id);

    void deleteById(Long id);

    ProductDto applyDiscount(Long productId, BigDecimal discountPrice);

    ProductDto getProductOfTheDay();

}
