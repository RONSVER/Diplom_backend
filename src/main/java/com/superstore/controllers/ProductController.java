package com.superstore.controllers;

import com.superstore.controllers.swagger.ProductControllerSwagger;
import com.superstore.dto.ProductDto;
import com.superstore.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController implements ProductControllerSwagger {

    private ProductService service;

    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createProduct(productDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.editProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean hasDiscount,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        List<ProductDto> products = service.getProducts(minPrice, maxPrice, hasDiscount, categoryId, sortBy, order);
        return ResponseEntity.ok(products);
    }

//    протетировать, небыло времени проверить
    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/{productId}/discount")
    public ResponseEntity<ProductDto> applyDiscount(@PathVariable Long productId, @Valid @RequestBody DiscountRequest discountPrice) {
        return ResponseEntity.ok(service.applyDiscount(productId, discountPrice.discountPrice()));
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/product-of-the-day")
    public ResponseEntity<ProductDto> getProductOfTheDay() {
        return ResponseEntity.ok(service.getProductOfTheDay());
    }

    public record DiscountRequest(
            @NotNull
            BigDecimal discountPrice
    ) {}
}
