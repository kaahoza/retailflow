package com.anele.retailflow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        order = new Order();

        product = new Product();
        product.setSku("EGGS-001");
        product.setName("Free Range Eggs 12 Pack");
        product.setPrice(new BigDecimal("34.99"));
    }

    @Test
    void addItem_calculatesTotalAmount_correctly() {
        order.addItem(product, 3);

        assertThat(order.getTotalAmount()).isEqualByComparingTo(new BigDecimal("104.97")); // 34.99 * 3
        assertThat(order.getItems()).hasSize(1);
    }

    @Test
    void addItem_freezesUnitPriceAtSale() {
        order.addItem(product, 1);

        product.setPrice(new BigDecimal("99.99")); // price changes AFTER order was placed

        assertThat(order.getItems().get(0).getUnitPriceAtSale())
                .isEqualByComparingTo(new BigDecimal("34.99")); // order still reflects original price
    }

    @Test
    void markPaid_changesStatusFromPendingToPaid() {
        order.markPaid();

        assertThat(order.getStatus()).isEqualTo("PAID");
    }

    @Test
    void markPaid_rejectsAlreadyPaidOrder() {
        order.markPaid();

        assertThatThrownBy(() -> order.markPaid())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Only PENDING orders can be marked as paid");
    }

    @Test
    void cancel_rejectsAlreadyPaidOrder() {
        order.markPaid();

        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot cancel an order that has already been paid");
    }
}
