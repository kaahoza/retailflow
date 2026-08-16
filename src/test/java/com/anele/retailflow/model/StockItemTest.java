package com.anele.retailflow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockItemTest {

    private StockItem stockItem;

    @BeforeEach
    void setUp() {
        stockItem = new StockItem();
    }

    @Test
    void receiveStock_increasesQuantityOnHand() {
        stockItem.receiveStock(50);

        assertThat(stockItem.getQuantityOnHand()).isEqualTo(50);
        assertThat(stockItem.getAvailableQuantity()).isEqualTo(50);
    }

    @Test
    void receiveStock_rejectsZeroOrNegativeQuantity() {
        assertThatThrownBy(() -> stockItem.receiveStock(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be positive");

        assertThatThrownBy(() -> stockItem.receiveStock(-5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void reserve_reducesAvailableQuantity_notOnHand() {
        stockItem.receiveStock(100);

        stockItem.reserve(30);

        assertThat(stockItem.getQuantityOnHand()).isEqualTo(100); // unchanged
        assertThat(stockItem.getQuantityReserved()).isEqualTo(30);
        assertThat(stockItem.getAvailableQuantity()).isEqualTo(70);
    }

    @Test
    void reserve_rejectsQuantityExceedingAvailableStock() {
        stockItem.receiveStock(10);

        assertThatThrownBy(() -> stockItem.reserve(11))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot reserve more than available stock");
    }

    @Test
    void releaseReservation_returnsStockToAvailable() {
        stockItem.receiveStock(100);
        stockItem.reserve(40);

        stockItem.releaseReservation(15);

        assertThat(stockItem.getQuantityReserved()).isEqualTo(25);
        assertThat(stockItem.getAvailableQuantity()).isEqualTo(75);
    }

    @Test
    void releaseReservation_rejectsReleasingMoreThanReserved() {
        stockItem.receiveStock(100);
        stockItem.reserve(20);

        assertThatThrownBy(() -> stockItem.releaseReservation(21))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot release more than currently reserved");
    }
}