package com.superstore.mapper;

import com.superstore.dto.CartDto;
import com.superstore.dto.CartItemDto;
import com.superstore.entity.Cart;
import com.superstore.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto cartToCartDto(Cart entity);

}
