package com.anele.retailflow.repository;

import com.anele.retailflow.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerIdAndStatus(Long customerId, String status);
}