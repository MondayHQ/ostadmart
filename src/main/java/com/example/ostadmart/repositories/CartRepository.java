package com.example.ostadmart.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.CartEntity;
import com.example.ostadmart.models.UserEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity findByUserEntity(UserEntity userEntity);

}
