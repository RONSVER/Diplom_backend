package com.superstore.services;

import com.superstore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(Long id);
}
