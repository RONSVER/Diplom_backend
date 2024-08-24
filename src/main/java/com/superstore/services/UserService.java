package com.superstore.services;

import com.superstore.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    User save(User user);
    void deleteById(Long id);
}
