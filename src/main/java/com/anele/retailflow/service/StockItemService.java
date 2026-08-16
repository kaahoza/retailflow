package com.anele.retailflow.service;

import com.anele.retailflow.dto.ReceiveStockRequest;
import com.anele.retailflow.dto.StockItemResponse;
import com.anele.retailflow.exception.ResourceNotFoundException;
import com.anele.retailflow.model.Product;
import com.anele.retailflow.model.StockItem;
import com.anele.retailflow.model.Supplier;
import com.anele.retailflow.repository.ProductRepository;
import com.anele.retailflow.repository.StockItemRepository;
import com.anele.retailflow.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockItemService {

    private final StockItemRepository stockItemRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public StockItemService(StockItemRepository stockItemRepository,
                            ProductRepository productRepository,
                            SupplierRepository supplierRepository) {
        this.stockItemRepository = stockItemRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public StockItemResponse receiveStock(ReceiveStockRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id " + request.getProductId()));

        StockItem stockItem = stockItemRepository.findByProductId(product.getId())
                .orElseGet(() -> {
                    StockItem newItem = new StockItem();
                    newItem.setProduct(product);
                    return newItem;
                });

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Supplier not found with id " + request.getSupplierId()));
            stockItem.setSupplier(supplier);
        }

        // Business rule lives on the entity itself — see StockItem.receiveStock()
        stockItem.receiveStock(request.getQuantity());

        StockItem saved = stockItemRepository.save(stockItem);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public StockItemResponse getStockForProduct(Long productId) {
        StockItem stockItem = stockItemRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No stock record found for product id " + productId));
        return toResponse(stockItem);
    }

    @Transactional(readOnly = true)
    public List<StockItemResponse> getAllStockItems() {
        return stockItemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private StockItemResponse toResponse(StockItem stockItem) {
        return new StockItemResponse(
                stockItem.getId(),
                stockItem.getProduct().getSku(),
                stockItem.getProduct().getName(),
                stockItem.getQuantityOnHand(),
                stockItem.getQuantityReserved(),
                stockItem.getAvailableQuantity(),
                stockItem.getReorderThreshold()
        );
    }
}