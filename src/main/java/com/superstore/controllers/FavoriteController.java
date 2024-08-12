package com.superstore.controllers;

import com.superstore.services.FavoriteService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/favorites")
@AllArgsConstructor
public class FavoriteController {

    private FavoriteService service;
}
