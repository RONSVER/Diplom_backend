package com.superstore.mapper;

import com.superstore.dto.ProductDto;
import com.superstore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "productId", source = "entity.productId"),
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "description", source = "entity.description"),
            @Mapping(target = "price", source = "entity.price"),
            @Mapping(target = "discountPrice", source = "entity.discountPrice"),
            @Mapping(target = "imageURL", source = "entity.imageURL"),
    })
    ProductDto productToProductDto(Product entity);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discountPrice", target = "discountPrice")
    @Mapping(source = "imageURL", target = "imageURL")
    Product productDtoToProduct(ProductDto entity);
}