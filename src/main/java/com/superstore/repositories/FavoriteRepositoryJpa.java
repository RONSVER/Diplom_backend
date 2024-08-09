package com.superstore.repositories;

import com.superstore.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepositoryJpa extends JpaRepository<Favorite, Long> {
}
