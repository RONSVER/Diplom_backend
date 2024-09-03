package com.superstore.mapper;

import com.superstore.dto.CategoryDto;
import com.superstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
            @Mapping(target = "categoryId", source = "categoryId"),
            @Mapping(target = "category", source = "name"),
    })
    CategoryDto categoryToCategoryDTO(Category entity);

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "category", target = "name")
    Category categoryDTOToCategory(CategoryDto categoryDto);
}