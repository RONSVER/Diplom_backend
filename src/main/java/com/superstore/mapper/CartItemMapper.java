package com.superstore.mapper;

import com.superstore.dto.CartItemDto;
import com.superstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mappings({
            @Mapping(target = "productId", source = "productId"),
            @Mapping(target = "quantity", source = "quantity"),
    })
    CartItemDto cartItemToCartItemDto(CartItem entity);

    @Mappings({
            @Mapping(source = "productId", target = "productId"),
            @Mapping(source = "quantity", target = "quantity"),
    })
    CartItem cartItemDtoToCartItem(CartItemDto entity);
}
