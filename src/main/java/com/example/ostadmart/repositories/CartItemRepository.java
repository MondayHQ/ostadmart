package com.example.ostadmart.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.CartEntity;
import com.example.ostadmart.models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);

    List<CartItem> findAllByCart(CartEntity cart);

    @Query("SELECT COALESCE(SUM(A.unitPrice * A.quantity), 0.0) FROM CartItem A WHERE A.cart.id=:cartId")
    Double getTotalAmountByCartId(@Param("cartId") Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem A WHERE A.cart.id=:cartId")
    void removeAllCartItemsByCartId(@Param("cartId") Long cartId);

}