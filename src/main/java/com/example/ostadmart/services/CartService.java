package com.example.ostadmart.services;

import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.models.CartEntity;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.models.CartItemEntity;
import com.example.ostadmart.mappers.CartItemMapper;
import com.example.ostadmart.dto.CartItemResponseDTO;
import com.example.ostadmart.dto.AddToCartRequestDTO;
import com.example.ostadmart.repositories.CartRepository;
import com.example.ostadmart.repositories.ProductRepository;
import com.example.ostadmart.repositories.CartItemRepository;
import com.example.ostadmart.exceptions.ProductNotFoundException;
import com.example.ostadmart.exceptions.InsufficientStockException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemMapper cartItemMapper,
            ProductRepository productRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemMapper = cartItemMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void createCart(UserEntity userEntity) {

        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserEntity(userEntity);
        cartEntity.setTotal_amount(0.0);

        cartRepository.save(cartEntity);
    }

    @Transactional
    public CartItemResponseDTO addProductToCart(@Valid @RequestBody AddToCartRequestDTO addToCartRequestDTO) throws ProductNotFoundException, InsufficientStockException {

        // GET Product
        ProductEntity productEntity = productRepository
                .findById(addToCartRequestDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (productEntity.getQty_left() < addToCartRequestDTO.getQty()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        // GET user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        Optional<CartItemEntity> existingCartItemEntity = cartItemRepository.findByCartEntityAndProductEntity(cartEntity, productEntity);

        int requestedTotal = addToCartRequestDTO.getQty() + existingCartItemEntity.map(CartItemEntity::getQty).orElse(0);
        if (productEntity.getQty_left() < requestedTotal) {
            throw new InsufficientStockException("Not enough stock available");
        }


        CartItemEntity cartItemEntity = existingCartItemEntity
                .map((item) -> {

                    item.setQty(requestedTotal);
                    cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

                    return item;

                })
                .orElseGet(() -> {

                    CartItemEntity newCartItemEntity = CartItemEntity.builder()
                            .cartEntity(cartEntity)
                            .productEntity(productEntity)
                            .unit_price(productEntity.getPrice())
                            .qty(addToCartRequestDTO.getQty())
                            .build();

                    cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

                    return cartItemRepository.save(newCartItemEntity);

                });

        return cartItemMapper.mapToResponseDTO(cartItemEntity);

    }

}
