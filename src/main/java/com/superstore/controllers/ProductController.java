package com.superstore.controllers;

import com.superstore.dto.ProductDto;
import com.superstore.entity.Product;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.mapper.ProductMapper;
import com.superstore.services.CategoryService;
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
    private CategoryService categoryService;
    private ProductMapper productMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);

        if (!categoryService.existsByName(productDto.category())) {
            throw new CategoryNotFoundException("Category with name " + productDto.category() + " not found");
        }

        product.setCategory(categoryService.findByName(productDto.category()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper.productToProductDto(service.save(product)));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<ProductDto> editProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        product.setProductId(id);

        if (!categoryService.existsByName(productDto.category())) {
            throw new CategoryNotFoundException("Category with name " + productDto.category() + " not found");
        }

        product.setCategory(categoryService.findByName(productDto.category()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.productToProductDto(service.editProduct(id, product)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
