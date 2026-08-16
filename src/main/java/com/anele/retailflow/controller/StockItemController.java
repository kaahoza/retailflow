package com.anele.retailflow.controller;

import com.anele.retailflow.dto.ReceiveStockRequest;
import com.anele.retailflow.dto.StockItemResponse;
import com.anele.retailflow.service.StockItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @PostMapping("/receive")
    public ResponseEntity<StockItemResponse> receiveStock(@Valid @RequestBody ReceiveStockRequest request) {
        return ResponseEntity.ok(stockItemService.receiveStock(request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<StockItemResponse> getStockForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockItemService.getStockForProduct(productId));
    }

    @GetMapping
    public ResponseEntity<List<StockItemResponse>> getAllStockItems() {
        return ResponseEntity.ok(stockItemService.getAllStockItems());
    }
}
