package com.superstore.mapper;

import com.superstore.dto.CartItemDto;
import com.superstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemDto cartItemToCartItemDto(CartItem entity);

    CartItem cartItemDtoToCartItem(CartItemDto entity);
}
