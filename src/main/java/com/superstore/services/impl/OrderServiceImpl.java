package com.superstore.services.impl;

import com.superstore.controllers.OrderController;
import com.superstore.dto.OrderDto;
import com.superstore.dto.OrderItemDto;
import com.superstore.entity.*;
import com.superstore.exceptions.CartNotFoundException;
import com.superstore.exceptions.EmptyCartException;
import com.superstore.exceptions.InvalidOrderStatusException;
import com.superstore.exceptions.OrderNotFoundException;
import com.superstore.mapper.OrderMapper;
import com.superstore.repository.CartRepository;
import com.superstore.repository.OrderItemRepository;
import com.superstore.repository.OrderRepository;
import com.superstore.services.CartService;
import com.superstore.services.OrderService;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository dao;
    private final UserService userService;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;


    @Override
    @Transactional
    public OrderDto createOrder(OrderController.CreateOrderRequest request) {
        long userId = userService.getCurrentUserId();

        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user id " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot create order. Cart is empty.");
        }

        Order order = new Order();
        User user = cart.getUser();
        order.setUser(user);
        order.setContactPhone(user.getPhoneNumber());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setDeliveryAddress(request.deliveryAddress());
        order.setDeliveryMethod(request.deliveryMethod().toString());
        order.setStatus(Order.Status.NEW);
        dao.save(order);

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getDiscountPrice() != null ? cartItem.getProduct().getDiscountPrice() : cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }
        List<OrderItemDto> orderItemDtos = order.getOrderItems()
                .stream()
                .map(orderMapper::orderItemToOrderItemDto)
                .toList();

        cartService.clearCart();

        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = dao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));
        List<OrderItemDto> orderItemDtos = order.getOrderItems()
                .stream()
                .map(orderMapper::orderItemToOrderItemDto)
                .toList();
        return orderMapper.orderToOrderDto(order);

    }

    @Override
    public List<OrderDto> getOrderHistory() {
        long userId = userService.getCurrentUserId();
        List<Order> orders = dao.findByUserUserId(userId);
        return orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderStatus(Long orderId) {
        Order order = dao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = dao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == Order.Status.NEW || order.getStatus() == Order.Status.PROCESSING) {
            order.setStatus(Order.Status.CANCELLED);
            order.setUpdatedAt(LocalDateTime.now());
            dao.save(order);
        } else {
            throw new InvalidOrderStatusException("Order cannot be cancelled in its current status: " + order.getStatus());
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, Order.Status status) {
        Order order = dao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        dao.save(order);
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void updateOrderStatuses() {

        List<Order> orders = dao.findAllByStatusNotIn(Arrays.asList(Order.Status.DELIVERED, Order.Status.CANCELLED));

        for (Order order : orders) {
            switch (order.getStatus()) {
                case NEW:
                    order.setStatus(Order.Status.PROCESSING);
                    break;
                case PROCESSING:
                    order.setStatus(Order.Status.SHIPPED);
                    break;
                case SHIPPED:
                    order.setStatus(Order.Status.DELIVERED);
                    break;
                default:
                    break;
            }
            order.setUpdatedAt(LocalDateTime.now());
            dao.save(order);
        }
    }

}
