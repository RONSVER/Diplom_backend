package com.superstore.services.impl;

import com.superstore.repository.ProductRepository;
import com.superstore.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImplOne implements ProductService {

    private ProductRepository repository;
}
