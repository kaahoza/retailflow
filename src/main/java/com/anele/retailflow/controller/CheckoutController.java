package com.anele.retailflow.controller;

import com.anele.retailflow.dto.OrderResponse;
import com.anele.retailflow.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<OrderResponse> checkout(@PathVariable Long customerId) {
        return ResponseEntity.ok(checkoutService.checkout(customerId));
    }
}