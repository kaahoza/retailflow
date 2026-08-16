package com.anele.retailflow.repository;

import com.anele.retailflow.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByProductId(Long productId);
}