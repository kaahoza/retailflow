package com.anele.retailflow.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 30)
    private String method;

    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "transaction_ref", length = 100)
    private String transactionRef;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Payment() {}

    /** Simulates processing — in a real system this would call a payment gateway. */
    public void markSuccessful(String transactionRef) {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Payment already processed with status: " + this.status);
        }
        this.status = "SUCCESSFUL";
        this.transactionRef = transactionRef;
    }

    public void markFailed() {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Payment already processed with status: " + this.status);
        }
        this.status = "FAILED";
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getStatus() { return status; }
    public String getTransactionRef() { return transactionRef; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}