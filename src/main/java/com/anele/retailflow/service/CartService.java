package com.anele.retailflow.service;

import com.anele.retailflow.dto.AddToCartRequest;
import com.anele.retailflow.dto.CartResponse;
import com.anele.retailflow.exception.ResourceNotFoundException;
import com.anele.retailflow.model.Cart;
import com.anele.retailflow.model.CartItem;
import com.anele.retailflow.model.Customer;
import com.anele.retailflow.model.Product;
import com.anele.retailflow.model.StockItem;
import com.anele.retailflow.repository.CartRepository;
import com.anele.retailflow.repository.CustomerRepository;
import com.anele.retailflow.repository.ProductRepository;
import com.anele.retailflow.repository.StockItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockItemRepository stockItemRepository;

    public CartService(CartRepository cartRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       StockItemRepository stockItemRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockItemRepository = stockItemRepository;
    }

    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id " + request.getCustomerId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id " + request.getProductId()));

        StockItem stockItem = stockItemRepository.findByProductId(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No stock record found for product id " + product.getId()));

        Cart cart = cartRepository.findByCustomerIdAndStatus(customer.getId(), "ACTIVE")
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return newCart;
                });

        // Business rule (AC-001/002/003) enforced inside Cart itself
        cart.addItem(product, request.getQuantity(), stockItem.getAvailableQuantity());

        Cart saved = cartRepository.save(cart);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CartResponse getActiveCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerIdAndStatus(customerId, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active cart found for customer id " + customerId));
        return toResponse(cart);
    }

    private CartResponse toResponse(Cart cart) {
        List<CartResponse.CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        BigDecimal subtotal = itemResponses.stream()
                .map(CartResponse.CartItemResponse::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), cart.getStatus(), itemResponses, subtotal);
    }

    private CartResponse.CartItemResponse toItemResponse(CartItem item) {
        BigDecimal unitPrice = item.getProduct().getPrice();
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartResponse.CartItemResponse(
                item.getProduct().getSku(),
                item.getProduct().getName(),
                item.getQuantity(),
                unitPrice,
                lineTotal
        );
    }
}