package com.anele.retailflow.repository;

import com.anele.retailflow.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi.product.id, oi.product.sku, oi.product.name, " +
            "SUM(oi.quantity), SUM(oi.quantity * oi.unitPriceAtSale) " +
            "FROM OrderItem oi WHERE oi.order.status = 'PAID' " +
            "GROUP BY oi.product.id, oi.product.sku, oi.product.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopSellingProducts();
}