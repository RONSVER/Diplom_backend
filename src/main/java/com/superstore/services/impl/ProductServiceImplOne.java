package com.superstore.services.impl;

import com.superstore.repository.ProductRepository;
import com.superstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplOne implements ProductService {

    @Autowired
    ProductRepository repository;
}
