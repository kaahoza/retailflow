package com.anele.retailflow.dto;

import java.math.BigDecimal;

public class TopProductResponse {
    private Long productId;
    private String sku;
    private String name;
    private long totalQuantitySold;
    private BigDecimal totalRevenue;

    public TopProductResponse(Long productId, String sku, String name,
                              long totalQuantitySold, BigDecimal totalRevenue) {
        this.productId = productId;
        this.sku = sku;
        this.name = name;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public Long getProductId() { return productId; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public long getTotalQuantitySold() { return totalQuantitySold; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
}