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
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.models.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    Optional<CartItemEntity> findByCartEntityAndProductEntity(CartEntity cartEntity, ProductEntity productEntity);

    List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

    @Query("SELECT COALESCE(SUM(A.unit_price * A.qty), 0.0) FROM CartItemEntity A WHERE A.cartEntity.id=:cartId")
    Double getTotalAmountByCartId(@Param("cartId") Long cartId);

    @Modifying
    @Query("DELETE FROM CartItemEntity A WHERE A.cartEntity.id=:cartId")
    void removeAllCartItemsByCartId(@Param("cartId") Long cartId);

}