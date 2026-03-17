package com.example.ostadmart.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.models.User;
import com.example.ostadmart.models.Product;
import com.example.ostadmart.models.CartItem;
import com.example.ostadmart.models.CartEntity;
import com.example.ostadmart.dto.CartItemResponse;
import com.example.ostadmart.mappers.CartItemMapper;
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

    public void createCart(User user) {

        CartEntity cartEntity = new CartEntity();
        cartEntity.setUser(user);
        cartEntity.setTotalAmount(0.0);

        cartRepository.save(cartEntity);
    }

    @Transactional
    public CartItemResponse addProductToCart(AddToCartRequestDTO addToCartRequestDTO) throws ProductNotFoundException, InsufficientStockException {

        // GET Product
        Product product = productRepository
                .findById(addToCartRequestDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getQuantityLeft() < addToCartRequestDTO.getQty()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        // GET user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        Optional<CartItem> existingCartItemEntity = cartItemRepository
                .findByCart_IdAndProduct_Id(cartEntity.getId(), product.getId());

        int requestedTotal = addToCartRequestDTO.getQty() + existingCartItemEntity.map(CartItem::getQuantity).orElse(0);
        if (product.getQuantityLeft() < requestedTotal) {
            throw new InsufficientStockException("Not enough stock available");
        }

        CartItem cartItem = existingCartItemEntity
                .map((item) -> {

                    item.setQuantity(requestedTotal);
                    cartItemRepository.flush();

                    cartEntity.setTotalAmount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

                    return item;

                })
                .orElseGet(() -> {

                    CartItem newCartItem = CartItem.builder()
                            .cart(cartEntity)
                            .product(product)
                            .unitPrice(product.getPrice())
                            .quantity(addToCartRequestDTO.getQty())
                            .build();

                    CartItem savedCartItem = cartItemRepository.save(newCartItem);
                    cartEntity.setTotalAmount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

                    return savedCartItem;

                });

        return cartItemMapper.mapToResponseDTO(cartItem);

    }

    public List<CartItemResponse> getAllCartItems() {

        // GET Authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        List<CartItem> cartItemEntities = cartItemRepository.findAllByCart(cartEntity);

        return cartItemEntities.stream().map(cartItemMapper::mapToResponseDTO).toList();

    }

    @Transactional
    public CartItemResponse updateCartItem(
            Long id,
            UpdateCartItemRequestDTO updateCartItemRequestDTO
    ) throws ProductNotFoundException, InsufficientStockException {

        // GET Authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        // Find the cart item
        CartItem existingCartItem = cartItemRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Item not found in the cart"));

        if (existingCartItem.getProduct().getQuantityLeft() < updateCartItemRequestDTO.getQty()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        existingCartItem.setQuantity(updateCartItemRequestDTO.getQty());
        cartItemRepository.flush();

        cartEntity.setTotalAmount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

        return cartItemMapper.mapToResponseDTO(existingCartItem);

    }

    @Transactional
    public void removeCartItem(Long id) throws ProductNotFoundException {

        // GET Authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        // Find the cart item
        CartItem existingCartItem = cartItemRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Item not found in the cart"));

        cartItemRepository.delete(existingCartItem);
        cartItemRepository.flush();

        cartEntity.setTotalAmount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

    }

    @Transactional
    public void clearCart() {

        // GET Authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GET cart
        CartEntity cartEntity = cartRepository.findByUser_Id(user.getId());

        cartItemRepository.removeAllCartItemsByCartId(cartEntity.getId());
        cartRepository.flush();

        cartEntity.setTotalAmount(cartItemRepository.getTotalAmountByCartId(cartEntity.getId()));

    }

}
