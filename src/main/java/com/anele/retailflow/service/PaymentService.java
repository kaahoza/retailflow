package com.anele.retailflow.service;

import com.anele.retailflow.dto.PaymentRequest;
import com.anele.retailflow.dto.PaymentResponse;
import com.anele.retailflow.exception.ResourceNotFoundException;
import com.anele.retailflow.model.Order;
import com.anele.retailflow.model.Payment;
import com.anele.retailflow.repository.OrderRepository;
import com.anele.retailflow.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id " + request.getOrderId()));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(request.getMethod());

        // Simulated gateway call — a real integration (Stripe, PayFast, etc.)
        // would happen here instead of this immediate success.
        String simulatedTransactionRef = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        payment.markSuccessful(simulatedTransactionRef);

        // If this line throws (e.g. order already paid), @Transactional rolls back
        // the Payment insert above too — they succeed or fail together.
        order.markPaid();

        Payment savedPayment = paymentRepository.save(payment);

        return toResponse(savedPayment, order);
    }

    private PaymentResponse toResponse(Payment payment, Order order) {
        return new PaymentResponse(
                payment.getId(),
                order.getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getTransactionRef(),
                order.getStatus(),
                payment.getCreatedAt()
        );
    }
}