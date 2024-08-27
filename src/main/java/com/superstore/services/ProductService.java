package com.superstore.services;

import com.superstore.dto.ProductDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto editProduct(Long productId, ProductDto productDto);

    void deleteById(Long id);
}
