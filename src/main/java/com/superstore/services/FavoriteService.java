package com.superstore.services;

import com.superstore.entity.Favorite;

import java.util.List;

public interface FavoriteService {

    List<Favorite> findByUserUsername(String username);
}
