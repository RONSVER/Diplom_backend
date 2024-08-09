package com.superstore.services.impl;

import com.superstore.repositories.FavoriteRepository;
import com.superstore.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImplOne implements FavoriteService {

    @Autowired
    FavoriteRepository repository;
}
