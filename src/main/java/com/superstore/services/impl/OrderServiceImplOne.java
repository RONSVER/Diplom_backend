package com.superstore.services.impl;

import com.superstore.repositories.OrderRepositoryJpa;
import com.superstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImplOne implements OrderService {

    @Autowired
    OrderRepositoryJpa repository;
}
