package com.example.ostadmart.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
