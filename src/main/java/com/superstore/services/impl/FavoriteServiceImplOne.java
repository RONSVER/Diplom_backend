package com.superstore.services.impl;

import com.superstore.repository.FavoriteRepository;
import com.superstore.services.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteServiceImplOne implements FavoriteService {

    private FavoriteRepository repository;
}
