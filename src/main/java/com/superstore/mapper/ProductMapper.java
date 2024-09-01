package com.superstore.mapper;

import com.superstore.dto.ProductDto;
import com.superstore.entity.Product;
import com.superstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "productId", source = "productId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "discountPrice", source = "discountPrice"),
            @Mapping(target = "imageURL", source = "imageURL"),
            @Mapping(target = "categoryId", source = "category.categoryId")
    })
    ProductDto productToProductDto(Product entity);

    @Mappings({
            @Mapping(target = "productId", source = "productId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "discountPrice", source = "discountPrice"),
            @Mapping(target = "imageURL", source = "imageURL"),
            @Mapping(target = "category.categoryId", source = "categoryId")
    })
    Product productDtoToProduct(ProductDto productDto);
}