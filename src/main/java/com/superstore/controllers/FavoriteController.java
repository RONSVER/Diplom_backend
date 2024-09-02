package com.superstore.controllers;

import com.superstore.dto.FavoriteDto;
import com.superstore.entity.Favorite;
import com.superstore.mapper.FavoriteMapper;
import com.superstore.repository.UserRepository;
import com.superstore.services.FavoriteService;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final FavoriteMapper favoriteMapper;

//    TODO: доделать
    @GetMapping
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    public ResponseEntity<List<FavoriteDto>> getUserFavorites(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        favoriteService
                                .findByUser_Name(userDetails.getUsername())
                                .stream()
                                .map(favoriteMapper::favoriteToFavoriteDto)
                                .collect(Collectors.toList())
                );
    }

    @PostMapping("/{productId}")
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    public ResponseEntity<FavoriteDto> addFavorite(@PathVariable Long productId) {
        FavoriteDto favoriteDto = favoriteService.addFavorite(productId);
        return new ResponseEntity<>(favoriteDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('Client') or hasAuthority('Administrator')")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long productId) {
        favoriteService.removeFavorite(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
