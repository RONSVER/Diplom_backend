package com.superstore.services.impl;

import com.superstore.entity.Favorite;
import com.superstore.repository.FavoriteRepository;
import com.superstore.services.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository dao;

    @Override
    public List<Favorite> findByUser_name(String username) {
        return dao.findByUser_name(username);
    }
}
