package com.superstore.repositories;

import com.superstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJpa extends JpaRepository<Product, Long> {
}
