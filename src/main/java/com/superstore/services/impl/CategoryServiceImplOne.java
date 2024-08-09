package com.superstore.services.impl;

import com.superstore.repositories.CategoryRepositoryJpa;
import com.superstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImplOne implements CategoryService {

    @Autowired
    CategoryRepositoryJpa repository;
}
