package com.superstore.controllers.swagger;

import com.superstore.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryControllerSwagger {

    @Operation(
            summary = "Добавление новой категории товаров",
            description = "Добавляет новую категорию товаров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Создано"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    @PostMapping
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto);

    @Operation(
            summary = "Редактирование категории товаров",
            description = "Редактирует категории товаров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto);

    @Operation(
            summary = "Получение списка категорий товаров",
            description = "Возвращает список категорий товаров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
            }
    )
    @GetMapping
    ResponseEntity<List<CategoryDto>> getAllCategory();

    @Operation(
            summary = "Получить категорию по ID",
            description = "Возвращает пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<CategoryDto> findById(@PathVariable Long id);

    @Operation(
            summary = "Удалить по ID",
            description = "Удаляет категорию на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    ResponseEntity<Void> deleteById(@PathVariable Long id);
}
