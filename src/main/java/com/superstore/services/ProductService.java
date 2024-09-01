package com.superstore.services;

import com.superstore.dto.ProductDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto editProduct(Long productId, ProductDto productDto);

    ProductDto getProductById(Long productId);

    void deleteById(Long id);
}
