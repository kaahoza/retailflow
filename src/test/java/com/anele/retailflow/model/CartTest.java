package com.anele.retailflow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {

    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        cart = new Cart();

        product = new Product();
        product.setSku("MILK-001");
        product.setName("Full Cream Milk 1L");
        product.setPrice(new BigDecimal("18.99"));
    }

    @Test
    void addItem_addsNewProductToCart_whenStockAvailable() {
        cart.addItem(product, 2, 10); // requesting 2, 10 available

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    void addItem_accumulatesQuantity_forExistingProduct() {
        cart.addItem(product, 2, 10);
        cart.addItem(product, 3, 10);

        assertThat(cart.getItems()).hasSize(1); // still one line item, not two
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    void addItem_rejectsQuantityExceedingAvailableStock() {
        assertThatThrownBy(() -> cart.addItem(product, 11, 10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("only 10 more available");
    }

    @Test
    void addItem_rejectsWhenAccumulatedQuantityExceedsStock() {
        cart.addItem(product, 7, 10); // 7 in cart, 3 left available

        assertThatThrownBy(() -> cart.addItem(product, 4, 10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("only 3 more available");
    }

    @Test
    void addItem_rejectsOutOfStockProduct() {
        assertThatThrownBy(() -> cart.addItem(product, 1, 0))
                .isInstanceOf(IllegalStateException.class);
    }
}