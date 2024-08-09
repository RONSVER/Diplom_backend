package com.superstore.services.impl;

import com.superstore.repositories.OrderRepository;
import com.superstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImplOne implements OrderService {

    @Autowired
    OrderRepository repository;
}
