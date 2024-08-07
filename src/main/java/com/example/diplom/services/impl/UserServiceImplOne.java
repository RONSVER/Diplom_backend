package com.example.diplom.services.impl;

import com.example.diplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplOne implements UserService {

    @Autowired
    JpaRepository repository;
}
