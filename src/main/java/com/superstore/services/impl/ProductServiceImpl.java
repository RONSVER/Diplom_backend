package com.superstore.services.impl;

import com.superstore.entity.Product;
import com.superstore.exceptions.ProductNotFoundException;
import com.superstore.repository.ProductRepository;
import com.superstore.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository dao;

    @Override
    public Product save(Product product) {
        return dao.save(product);
    }

    @Override
    public Product editProduct(Long productId, Product newProduct) {
        if (dao.existsById(productId)) {
            newProduct.setProductId(productId);
            return dao.save(newProduct);
        } else {
            logger.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
    }

    @Override
    public void deleteById(Long productId) {
        if (dao.findById(productId).isEmpty()) {
            logger.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        dao.deleteById(productId);
    }
}
