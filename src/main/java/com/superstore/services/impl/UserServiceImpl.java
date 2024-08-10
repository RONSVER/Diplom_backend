package com.superstore.services.impl;

import com.superstore.entity.User;
import com.superstore.repository.UserRepository;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository dao;

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return dao.findById(id);
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