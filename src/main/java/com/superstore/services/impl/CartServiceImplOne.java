package com.superstore.services.impl;

import com.superstore.repositories.CartRepositoryJpa;
import com.superstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplOne implements CartService {

    @Autowired
    CartRepositoryJpa repository;
}
