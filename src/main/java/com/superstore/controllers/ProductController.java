package com.superstore.controllers;

import com.superstore.dto.ProductDto;
import com.superstore.entity.Product;
import com.superstore.mapper.ProductMapper;
import com.superstore.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService service;
    private ProductMapper productMapper;


    @PostMapping
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto) {
        //error with type of product
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.productToProductDto(service.save(productDto)));
    }
}
