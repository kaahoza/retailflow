package com.anele.retailflow.controller;

import com.anele.retailflow.dto.AddToCartRequest;
import com.anele.retailflow.dto.CartResponse;
import com.anele.retailflow.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getActiveCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getActiveCart(customerId));
    }

    private Long request(Long customerId) {
        return customerId;
    }
}