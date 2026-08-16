package com.anele.retailflow.repository;

import com.anele.retailflow.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o " +
            "WHERE o.status = 'PAID' AND o.createdAt BETWEEN :start AND :end")
    BigDecimal sumRevenueBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE o.status = 'PAID' AND o.createdAt BETWEEN :start AND :end")
    long countPaidOrdersBetween(LocalDateTime start, LocalDateTime end);
}