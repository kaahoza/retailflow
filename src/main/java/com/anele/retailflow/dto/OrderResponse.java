package com.anele.retailflow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long orderId;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    public OrderResponse(Long orderId, String status, BigDecimal totalAmount,
                         List<OrderItemResponse> items, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
        this.createdAt = createdAt;
    }

    public Long getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemResponse> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static class OrderItemResponse {
        private String productSku;
        private String productName;
        private int quantity;
        private BigDecimal unitPriceAtSale;

        public OrderItemResponse(String productSku, String productName, int quantity, BigDecimal unitPriceAtSale) {
            this.productSku = productSku;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPriceAtSale = unitPriceAtSale;
        }

        public String getProductSku() { return productSku; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public BigDecimal getUnitPriceAtSale() { return unitPriceAtSale; }
    }
}