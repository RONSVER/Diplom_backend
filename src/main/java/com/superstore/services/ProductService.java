package com.superstore.services;

import com.superstore.entity.Product;

public interface ProductService {

    Product save(Product product);

    Product editProduct(Long productId, Product product);

    void deleteById(Long id);
}
