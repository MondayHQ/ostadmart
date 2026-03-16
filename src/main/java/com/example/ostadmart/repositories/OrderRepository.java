package com.example.ostadmart.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
