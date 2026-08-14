package com.anele.retailflow.repository;

import com.anele.retailflow.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}