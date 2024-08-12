package com.superstore.services.impl;

import com.superstore.repository.CartRepository;
import com.superstore.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImplOne implements CartService {

    private CartRepository repository;
}
