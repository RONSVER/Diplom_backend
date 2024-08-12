package com.superstore.mapper;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
            @Mapping(target = "categoryId", source = "entity.categoryId"),
            @Mapping(target = "name", source = "entity.name"),
    })
    CategoryDto categoryToCategoryDTO(Category entity);
}