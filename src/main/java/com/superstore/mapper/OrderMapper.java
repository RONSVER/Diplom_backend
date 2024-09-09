package com.superstore.mapper;

import com.superstore.dto.OrderDto;
import com.superstore.dto.OrderItemDto;
import com.superstore.entity.Order;
import com.superstore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "orderItems", target = "items")
    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "priceAtPurchase", target = "price")
    OrderItemDto orderItemToOrderItemDto(OrderItem item);

    OrderItem orderItemDtoToOrderItem(OrderItemDto item);

}
