package com.anele.retailflow.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private Long cartId;
    private String status;
    private List<CartItemResponse> items;
    private BigDecimal subtotal;

    public CartResponse(Long cartId, String status, List<CartItemResponse> items, BigDecimal subtotal) {
        this.cartId = cartId;
        this.status = status;
        this.items = items;
        this.subtotal = subtotal;
    }

    public Long getCartId() { return cartId; }
    public String getStatus() { return status; }
    public List<CartItemResponse> getItems() { return items; }
    public BigDecimal getSubtotal() { return subtotal; }

    public static class CartItemResponse {
        private String productSku;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;

        public CartItemResponse(String productSku, String productName, int quantity,
                                BigDecimal unitPrice, BigDecimal lineTotal) {
            this.productSku = productSku;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.lineTotal = lineTotal;
        }

        public String getProductSku() { return productSku; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public BigDecimal getLineTotal() { return lineTotal; }
    }
}