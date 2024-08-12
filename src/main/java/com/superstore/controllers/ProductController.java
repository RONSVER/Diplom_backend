package com.superstore.controllers;

import com.superstore.services.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService service;
}
