package com.example.ostadmart.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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
import com.example.ostadmart.dto.UpdateCartItemRequestDTO;
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
    public CartItemResponseDTO addProductToCart(AddToCartRequestDTO addToCartRequestDTO) throws ProductNotFoundException, InsufficientStockException {

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
                    cartItemRepository.flush();

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

                    CartItemEntity savedCartItemEntity = cartItemRepository.save(newCartItemEntity);
                    cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

                    return savedCartItemEntity;

                });

        return cartItemMapper.mapToResponseDTO(cartItemEntity);

    }

    public List<CartItemResponseDTO> getAllCartItems() {

        // GET Authenticated user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByCartEntity(cartEntity);

        return cartItemEntities.stream().map(cartItemMapper::mapToResponseDTO).toList();

    }

    @Transactional
    public CartItemResponseDTO updateCartItem(UpdateCartItemRequestDTO updateCartItemRequestDTO) throws ProductNotFoundException, InsufficientStockException {

        // GET Authenticated user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET Product
        ProductEntity productEntity = productRepository
                .findById(updateCartItemRequestDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        // Find the cart item
        CartItemEntity existingCartItemEntity = cartItemRepository
                .findByCartEntityAndProductEntity(cartEntity, productEntity)
                .orElseThrow(() -> new ProductNotFoundException("Product not found in the cart"));


        if (productEntity.getQty_left() < updateCartItemRequestDTO.getQty()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        existingCartItemEntity.setQty(updateCartItemRequestDTO.getQty());
        cartItemRepository.flush();

        cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

        return cartItemMapper.mapToResponseDTO(existingCartItemEntity);

    }

    @Transactional
    public void removeCartItem(Long productId) throws ProductNotFoundException {

        // GET Authenticated user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET Product
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        // Find the cart item
        CartItemEntity existingCartItemEntity = cartItemRepository
                .findByCartEntityAndProductEntity(cartEntity, productEntity)
                .orElseThrow(() -> new ProductNotFoundException("Product not found in the cart"));

        cartItemRepository.delete(existingCartItemEntity);
        cartItemRepository.flush();

        cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

    }

    @Transactional
    public void clearCart() {

        // GET Authenticated user
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

        cartItemRepository.removeAllCartItemsByCartId(cartEntity.getId());
        cartRepository.flush();

        cartEntity.setTotal_amount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

    }

}
