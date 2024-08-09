package com.superstore.controllers;

import com.superstore.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/favorites")
public class FavoriteController {

    @Autowired
    FavoriteService service;
}
