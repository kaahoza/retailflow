package com.anele.retailflow.repository;

import com.anele.retailflow.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByProductId(Long productId);

    @Query("SELECT s FROM StockItem s WHERE (s.quantityOnHand - s.quantityReserved) <= s.reorderThreshold")
    List<StockItem> findLowStockItems();
}