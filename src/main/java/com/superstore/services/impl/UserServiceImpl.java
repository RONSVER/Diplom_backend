package com.superstore.services.impl;

import com.superstore.entity.User;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.repository.UserRepository;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository dao;

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found", id);
                    return new UserNotFoundException("User with ID " + id + " not found");
                });
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with Email " + email + " not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return dao.existsByUserId(id);
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
