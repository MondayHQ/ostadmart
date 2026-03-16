package com.example.ostadmart.services;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import com.example.ostadmart.enums.OrderStatus;
import com.example.ostadmart.exceptions.CartEmptyException;
import com.example.ostadmart.exceptions.InsufficientStockException;
import com.example.ostadmart.mappers.OrderMapper;
import com.example.ostadmart.models.*;
import com.example.ostadmart.repositories.CartItemRepository;
import com.example.ostadmart.repositories.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.dto.OrderResponse;
import com.example.ostadmart.dto.OrderCreateRequest;
import com.example.ostadmart.repositories.CartRepository;
import com.example.ostadmart.repositories.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest) throws CartEmptyException, InsufficientStockException {

        // GET Authenticated user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByCartEntity(cartEntity);

        if (cartItemEntities.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        double totalAmount = 0;

        for (CartItemEntity cartItemEntity : cartItemEntities) {
            ProductEntity productEntity = cartItemEntity.getProductEntity();

            if (cartItemEntity.getQty() > productEntity.getQty_left()) {
                throw new InsufficientStockException("Insufficient stock for product: " + productEntity.getName());
            }

            totalAmount += cartItemEntity.getQty() * productEntity.getPrice();
        }

        // CREATE order
        Order order = Order.builder()
                .userEntity(userEntity)
                .totalAmount(totalAmount)
                .isPaid(false)
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemEntities) {

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .productEntity(cartItemEntity.getProductEntity())
                    .unitPrice(cartItemEntity.getProductEntity().getPrice())
                    .quantity(cartItemEntity.getQty())
                    .build();

            ProductEntity productEntity = cartItemEntity.getProductEntity();
            productEntity.setQty_left(productEntity.getQty_left() - cartItemEntity.getQty());
        }

        savedOrder.setOrderItemEntities(orderItems);
        orderItemRepository.saveAll(orderItems);

        // Clear cart
        cartService.clearCart();

        return orderMapper.mapToResponse(savedOrder);
    }

}
