package com.superstore.controllers.swagger;

import com.superstore.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductControllerSwagger {
    @PostMapping
    @Operation(
            summary = "Добавление нового товара",
            description = "Добавляет новый товар",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Товар создан"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    ResponseEntity<ProductDto> createProduct(@Parameter(
            name = "productDto",
            description = "Тело продукта",
            required = true,
            in = ParameterIn.HEADER
    ) @Valid @RequestBody ProductDto productDto);

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование товара",
            description = "Редактирует товар",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            }
    )
    ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Parameter(
            name = "productDto",
            description = "Тело новаого продукта",
            required = true,
            in = ParameterIn.HEADER
    ) @Valid @RequestBody ProductDto productDto);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление товара",
            description = "Удаляет товар на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар удалён"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            }
    )
    ResponseEntity<Void> deleteById(@PathVariable Long id);

    @Operation(
            summary = "Получить товар по ID",
            description = "Возвращает товар на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар найден"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable Long id);

    @Operation(
            summary = "Получение списка товаров",
            description = "Возвращает список товаров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
            }
    )
    @GetMapping
    ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean hasDiscount,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    );
}
