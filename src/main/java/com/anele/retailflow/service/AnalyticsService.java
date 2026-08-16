package com.anele.retailflow.service;

import com.anele.retailflow.dto.RevenueSummaryResponse;
import com.anele.retailflow.dto.StockItemResponse;
import com.anele.retailflow.dto.TopProductResponse;
import com.anele.retailflow.model.StockItem;
import com.anele.retailflow.repository.OrderItemRepository;
import com.anele.retailflow.repository.OrderRepository;
import com.anele.retailflow.repository.StockItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticsService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockItemRepository stockItemRepository;

    public AnalyticsService(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            StockItemRepository stockItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.stockItemRepository = stockItemRepository;
    }

    @Transactional(readOnly = true)
    public RevenueSummaryResponse getRevenueSummary(LocalDateTime start, LocalDateTime end) {
        BigDecimal totalRevenue = orderRepository.sumRevenueBetween(start, end);
        long orderCount = orderRepository.countPaidOrdersBetween(start, end);
        return new RevenueSummaryResponse(totalRevenue, orderCount, start, end);
    }

    @Transactional(readOnly = true)
    public List<TopProductResponse> getTopSellingProducts() {
        List<Object[]> rows = orderItemRepository.findTopSellingProducts();

        return rows.stream()
                .map(row -> new TopProductResponse(
                        (Long) row[0],
                        (String) row[1],
                        (String) row[2],
                        (Long) row[3],
                        (BigDecimal) row[4]
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockItemResponse> getLowStockItems() {
        List<StockItem> items = stockItemRepository.findLowStockItems();

        return items.stream()
                .map(this::toStockResponse)
                .toList();
    }

    private StockItemResponse toStockResponse(StockItem stockItem) {
        return new StockItemResponse(
                stockItem.getId(),
                stockItem.getProduct().getSku(),
                stockItem.getProduct().getName(),
                stockItem.getQuantityOnHand(),
                stockItem.getQuantityReserved(),
                stockItem.getAvailableQuantity(),
                stockItem.getReorderThreshold()
        );
    }
}