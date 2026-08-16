package com.anele.retailflow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private String transactionRef;
    private String orderStatus;
    private LocalDateTime createdAt;

    public PaymentResponse(Long paymentId, Long orderId, BigDecimal amount, String method,
                           String status, String transactionRef, String orderStatus,
                           LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionRef = transactionRef;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Long getPaymentId() { return paymentId; }
    public Long getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }
    public String getTransactionRef() { return transactionRef; }
    public String getOrderStatus() { return orderStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}