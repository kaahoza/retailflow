package com.anele.retailflow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueSummaryResponse {
    private BigDecimal totalRevenue;
    private long orderCount;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;

    public RevenueSummaryResponse(BigDecimal totalRevenue, long orderCount,
                                  LocalDateTime periodStart, LocalDateTime periodEnd) {
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public long getOrderCount() { return orderCount; }
    public LocalDateTime getPeriodStart() { return periodStart; }
    public LocalDateTime getPeriodEnd() { return periodEnd; }
}