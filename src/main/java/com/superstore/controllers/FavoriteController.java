package com.superstore.controllers;

import com.superstore.controllers.swagger.FavoriteControllerSwagger;
import com.superstore.dto.FavoriteDto;
import com.superstore.dto.ProductDto;
import com.superstore.services.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/favorites")
public class FavoriteController implements FavoriteControllerSwagger {

    private final FavoriteService favoriteService;

    @GetMapping
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    @Override
    public ResponseEntity<List<ProductDto>> getUserFavorites() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(favoriteService.getUserFavoriteProducts());
    }

    @PostMapping("/{productId}")
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    @Override
    public ResponseEntity<FavoriteDto> addFavorite(@PathVariable Long productId) {
        FavoriteDto favoriteDto = favoriteService.addFavorite(productId);
        return new ResponseEntity<>(favoriteDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    @Override
    public ResponseEntity<Void> removeFavorite(@PathVariable Long productId) {
        favoriteService.removeFavorite(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
