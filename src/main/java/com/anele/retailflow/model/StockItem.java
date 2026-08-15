package com.anele.retailflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_item")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Min(0)
    @Column(name = "quantity_on_hand", nullable = false)
    private int quantityOnHand = 0;

    @Min(0)
    @Column(name = "quantity_reserved", nullable = false)
    private int quantityReserved = 0;

    @Column(name = "reorder_threshold", nullable = false)
    private int reorderThreshold = 5;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
    }

    public StockItem() {}

    // --- Business logic lives here, not in the service ---

    /** Available stock = what's physically on hand minus what's already promised to other orders. */
    public int getAvailableQuantity() {
        return quantityOnHand - quantityReserved;
    }

    public void receiveStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity received must be positive");
        }
        this.quantityOnHand += quantity;
    }

    public void reserve(int quantity) {
        if (quantity > getAvailableQuantity()) {
            throw new IllegalStateException("Cannot reserve more than available stock");
        }
        this.quantityReserved += quantity;
    }

    public void releaseReservation(int quantity) {
        if (quantity > this.quantityReserved) {
            throw new IllegalStateException("Cannot release more than currently reserved");
        }
        this.quantityReserved -= quantity;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public int getQuantityOnHand() { return quantityOnHand; }
    public int getQuantityReserved() { return quantityReserved; }

    public int getReorderThreshold() { return reorderThreshold; }
    public void setReorderThreshold(int reorderThreshold) { this.reorderThreshold = reorderThreshold; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}