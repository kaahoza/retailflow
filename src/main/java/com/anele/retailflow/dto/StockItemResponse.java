package com.anele.retailflow.dto;

public class StockItemResponse {
    private Long id;
    private String productSku;
    private String productName;
    private int quantityOnHand;
    private int quantityReserved;
    private int availableQuantity;
    private int reorderThreshold;
    private boolean lowStock;

    public StockItemResponse(Long id, String productSku, String productName,
                             int quantityOnHand, int quantityReserved,
                             int availableQuantity, int reorderThreshold) {
        this.id = id;
        this.productSku = productSku;
        this.productName = productName;
        this.quantityOnHand = quantityOnHand;
        this.quantityReserved = quantityReserved;
        this.availableQuantity = availableQuantity;
        this.reorderThreshold = reorderThreshold;
        this.lowStock = availableQuantity <= reorderThreshold;
    }

    public Long getId() { return id; }
    public String getProductSku() { return productSku; }
    public String getProductName() { return productName; }
    public int getQuantityOnHand() { return quantityOnHand; }
    public int getQuantityReserved() { return quantityReserved; }
    public int getAvailableQuantity() { return availableQuantity; }
    public int getReorderThreshold() { return reorderThreshold; }
    public boolean isLowStock() { return lowStock; }
}