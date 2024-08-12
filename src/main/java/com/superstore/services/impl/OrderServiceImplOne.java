package com.superstore.services.impl;

import com.superstore.repository.OrderRepository;
import com.superstore.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImplOne implements OrderService {

    private OrderRepository repository;
}
