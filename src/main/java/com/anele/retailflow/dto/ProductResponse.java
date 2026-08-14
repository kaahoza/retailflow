package com.anele.retailflow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean active;
    private String categoryName;
    private LocalDateTime createdAt;

    public ProductResponse(Long id, String sku, String name, String description,
                           BigDecimal price, boolean active, String categoryName,
                           LocalDateTime createdAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public boolean isActive() { return active; }
    public String getCategoryName() { return categoryName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}