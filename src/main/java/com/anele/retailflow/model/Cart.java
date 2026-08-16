package com.anele.retailflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Cart() {}

    // --- Domain logic: this is where AC-001/002/003 live ---

    /**
     * Adds a product to the cart, or increases quantity if already present.
     * Enforces: quantity in cart must never exceed available stock (AC-002).
     */
    public void addItem(Product product, int quantityToAdd, int availableStock) {
        CartItem existing = items.stream()
                .filter(i -> java.util.Objects.equals(i.getProduct().getId(), product.getId()))                .findFirst()
                .orElse(null);

        int currentQuantityInCart = existing != null ? existing.getQuantity() : 0;
        int newTotal = currentQuantityInCart + quantityToAdd;

        if (newTotal > availableStock) {
            throw new IllegalStateException(
                    "Cannot add " + quantityToAdd + " of " + product.getName() +
                            " — only " + (availableStock - currentQuantityInCart) + " more available");
        }

        if (existing != null) {
            existing.setQuantity(newTotal);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(this);
            newItem.setProduct(product);
            newItem.setQuantity(quantityToAdd);
            items.add(newItem);
        }
    }

    public Long getId() { return id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<CartItem> getItems() { return items; }
}