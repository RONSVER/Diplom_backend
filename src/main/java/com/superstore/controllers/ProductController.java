package com.superstore.controllers;

import com.superstore.dto.ProductDto;
import com.superstore.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService service;

    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createProduct(productDto));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<ProductDto> editProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.editProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
