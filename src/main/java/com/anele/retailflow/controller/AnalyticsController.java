package com.anele.retailflow.controller;

import com.anele.retailflow.dto.RevenueSummaryResponse;
import com.anele.retailflow.dto.StockItemResponse;
import com.anele.retailflow.dto.TopProductResponse;
import com.anele.retailflow.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueSummaryResponse> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(analyticsService.getRevenueSummary(start, end));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductResponse>> getTopProducts() {
        return ResponseEntity.ok(analyticsService.getTopSellingProducts());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<StockItemResponse>> getLowStockItems() {
        return ResponseEntity.ok(analyticsService.getLowStockItems());
    }
}