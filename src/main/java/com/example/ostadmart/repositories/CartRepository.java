package com.example.ostadmart.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUser_Id(Long id);
}
