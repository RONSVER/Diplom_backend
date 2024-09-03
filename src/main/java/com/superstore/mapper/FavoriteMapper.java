package com.superstore.mapper;

import com.superstore.dto.FavoriteDto;
import com.superstore.entity.Favorite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteDto favoriteToFavoriteDto(Favorite favorite);
    Favorite favoriteDtoToFavorite (FavoriteDto favoriteDto);
}
