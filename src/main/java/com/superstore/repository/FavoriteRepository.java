package com.superstore.repository;

import com.superstore.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_Name(String username);
    List<Favorite> findByUserUserId(Long userId);
    boolean existsByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
    void deleteByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
}
