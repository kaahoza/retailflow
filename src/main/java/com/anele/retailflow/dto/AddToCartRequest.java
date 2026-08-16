package com.anele.retailflow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public AddToCartRequest() {}

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}