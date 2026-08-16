package com.anele.retailflow.service;

import com.anele.retailflow.dto.OrderResponse;
import com.anele.retailflow.exception.ResourceNotFoundException;
import com.anele.retailflow.model.Cart;
import com.anele.retailflow.model.CartItem;
import com.anele.retailflow.model.Order;
import com.anele.retailflow.model.StockItem;
import com.anele.retailflow.repository.CartRepository;
import com.anele.retailflow.repository.OrderRepository;
import com.anele.retailflow.repository.StockItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final StockItemRepository stockItemRepository;
    private final OrderRepository orderRepository;

    public CheckoutService(CartRepository cartRepository,
                           StockItemRepository stockItemRepository,
                           OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.stockItemRepository = stockItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse checkout(Long customerId) {
        Cart cart = cartRepository.findByCustomerIdAndStatus(customerId, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active cart found for customer id " + customerId));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }

        // STEP 1: Re-validate stock for every item (AC-002 from US-003 —
        // stock may have changed since items were added to the cart).
        // We do this as a separate loop BEFORE reserving anything, so we
        // never partially reserve an order that will fail halfway through.
        for (CartItem item : cart.getItems()) {
            StockItem stockItem = stockItemRepository.findByProductId(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No stock record for product " + item.getProduct().getSku()));

            if (item.getQuantity() > stockItem.getAvailableQuantity()) {
                throw new IllegalStateException(
                        "Insufficient stock for " + item.getProduct().getName() +
                                " — requested " + item.getQuantity() +
                                ", only " + stockItem.getAvailableQuantity() + " available");
            }
        }

        // STEP 2: All items validated — now actually reserve stock and build the order.
        Order order = new Order();
        order.setCustomer(cart.getCustomer());

        for (CartItem item : cart.getItems()) {
            StockItem stockItem = stockItemRepository.findByProductId(item.getProduct().getId())
                    .orElseThrow(); // safe: already validated above

            stockItem.reserve(item.getQuantity()); // <-- the method that's existed since Phase 2, finally used
            stockItemRepository.save(stockItem);

            order.addItem(item.getProduct(), item.getQuantity());
        }

        Order savedOrder = orderRepository.save(order);

        // STEP 3: Close out the cart — it's been converted into an order.
        cart.setStatus("CHECKED_OUT");
        cartRepository.save(cart);

        return toResponse(savedOrder);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
                .map(i -> new OrderResponse.OrderItemResponse(
                        i.getProduct().getSku(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitPriceAtSale()))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                itemResponses,
                order.getCreatedAt()
        );
    }
}