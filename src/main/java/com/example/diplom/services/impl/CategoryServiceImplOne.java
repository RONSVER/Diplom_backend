package com.example.diplom.services.impl;

import com.example.diplom.repositories.CategoryRepositoryJpa;
import com.example.diplom.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImplOne implements CategoryService {

    @Autowired
    CategoryRepositoryJpa repository;
}
