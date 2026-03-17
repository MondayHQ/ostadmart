package com.example.ostadmart.services;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.models.*;
import com.example.ostadmart.repositories.*;
import com.example.ostadmart.dto.OrderResponse;
import com.example.ostadmart.enums.OrderStatus;
import com.example.ostadmart.mappers.OrderMapper;
import com.example.ostadmart.dto.OrderCreateRequest;
import com.example.ostadmart.exceptions.CartEmptyException;
import com.example.ostadmart.exceptions.UserNotFoundException;
import com.example.ostadmart.exceptions.InsufficientStockException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final AuthRepository authRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest) throws CartEmptyException, InsufficientStockException, UserNotFoundException {

        // GET Authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();
        User user = authRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        List<CartItem> cartItemEntities = cartItemRepository.findAllByCart(cartEntity);

        if (cartItemEntities.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        double totalAmount = 0;

        for (CartItem cartItem : cartItemEntities) {
            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getQuantityLeft()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            totalAmount += cartItem.getQuantity() * product.getPrice();
        }

        // CREATE order
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .isPaid(false)
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItemEntities) {

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(cartItem.getProduct())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItems.add(orderItem);

            Product product = cartItem.getProduct();
            product.setQuantityLeft(product.getQuantityLeft() - cartItem.getQuantity());
        }

        savedOrder.setOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);

        // Clear cart
        cartService.clearCart();

        return orderMapper.mapToResponse(savedOrder);
    }

}
