package com.example.demo.stock.application;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.common.events.OrderStockFailedEvent;
import com.example.demo.common.exceptions.BusinessException;
import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.ProductInStock;
import com.example.demo.stock.ports.out.ProductRepoPort;
import com.example.demo.stock.ports.out.StockServicePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockService implements StockServicePort {
    private final ProductRepoPort productRepoPort;
    private final ApplicationEventPublisher publisher;

    public StockService(ProductRepoPort productRepoPort, ApplicationEventPublisher publisher) {
        this.productRepoPort = productRepoPort;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public void updateProductQuantity(OrderDetails orderDetails) {
        List<OrderedItem> items = orderDetails.items();
        Map<Integer,OrderedItem> itemsMap = items.stream().collect(Collectors.toMap(OrderedItem::productId, Function.identity()));
        List<Product> products = productRepoPort.getAllByIds(new ArrayList<>(itemsMap.keySet()));
        products.forEach(product -> {
            OrderedItem orderedItem = itemsMap.get(product.getId());
            validateQuantity(orderedItem, product);
            product.setAmountInStock(product.getAmountInStock() - orderedItem.quantity());
        });
       Map<Integer,Integer> productsQuantities = products.stream().collect(Collectors.toMap(Product::getId,Product::getAmountInStock));
       productRepoPort.updateQuantities(productsQuantities);
        publisher.publishEvent(new OrderProductStockVerifiedEvent(orderDetails));

    }

    @Transactional
    @Override
    public void rollbackProductQuantity(OrderDetails orderDetails, String message) {
        List<OrderedItem> items = orderDetails.items();
        Map<Integer,OrderedItem> itemsMap = items.stream().collect(Collectors.toMap(OrderedItem::productId, Function.identity()));
        List<Product> products = productRepoPort.getAllByIds(new ArrayList<>(itemsMap.keySet()));
        products.forEach(product -> {
            product.setAmountInStock(product.getAmountInStock() + itemsMap.get(product.getId()).quantity());
        });
        Map<Integer,Integer> productsQuantities = products.stream().collect(Collectors.toMap(Product::getId,Product::getAmountInStock));
        productRepoPort.updateQuantities(productsQuantities);
        publisher.publishEvent(new OrderStockFailedEvent(orderDetails.orderId(), message));
    }


    @Override
    public List<ProductInStock> getProducts(List<Integer> productIds) {
        return productRepoPort.getAllByIds(productIds).stream().map(p -> new ProductInStock(p.getId(), p.getPrice(), p.getAmountInStock())).toList();
    }
    @Override
    @Transactional
    public void cancelProductReservation(Exception e, OrderDetails orderDetails) {
        publisher.publishEvent(new OrderStockFailedEvent(orderDetails.orderId(), e.getMessage()));
    }
    private void validateQuantity(OrderedItem item, Product product) {
        if (product.getAmountInStock() < item.quantity()) {
            throw new BusinessException("Not enough stock for product " + item.productId());
        }
    }
}