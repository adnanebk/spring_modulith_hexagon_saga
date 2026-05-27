package com.example.demo.stock.application;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.data.StockedProduct;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.common.events.OrderStockFailedEvent;
import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.exeptions.NotEnoughStockException;
import com.example.demo.stock.ports.in.ProductRepoPort;
import com.example.demo.stock.ports.in.StockServicePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService implements StockServicePort {
    private final ProductRepoPort productRepoPort;
    private final ApplicationEventPublisher publisher;

    public StockService(ProductRepoPort productRepoPort, ApplicationEventPublisher publisher) {
        this.productRepoPort = productRepoPort;
        this.publisher = publisher;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateProductQuantity(OrderDetails orderDetails) {
        List<OrderedItem> items = orderDetails.items();
        List<Product> products = getCorrespondingProducts(items);
            for (OrderedItem item : items) {
                Product product = getCorrespondingProduct(item, products)
                        .orElseThrow(() -> new IllegalStateException("Product not found: " + item.productId()));
                validate(item, product);
                product.setAmountInStock(product.getAmountInStock() - item.quantity());
            }
            productRepoPort.saveAll(products);
            publisher.publishEvent(new OrderProductStockVerifiedEvent(orderDetails));

    }

    @Transactional
    @Override
    public void rollbackProductQuantity(OrderDetails orderDetails, String message) {
        List<OrderedItem> items = orderDetails.items();
        List<Product> products = getCorrespondingProducts(items);

        for (OrderedItem item : items) {
            getCorrespondingProduct(item, products).ifPresent(product ->
                    product.setAmountInStock(product.getAmountInStock() + item.quantity())
            );
        }

        productRepoPort.saveAll(products);
        publisher.publishEvent(new OrderStockFailedEvent(orderDetails.orderId(), message));
    }

    @Override
    public void cancelUpdateQuantity(Integer orderId, String message){
        publisher.publishEvent(new OrderStockFailedEvent(orderId, message));

    }

    @Override
    public List<StockedProduct> getProducts(List<Integer> productIds) {
        return productRepoPort.getAllByIds(productIds).stream().map(p -> new StockedProduct(p.getId(), p.getPrice(), p.getAmountInStock())).toList();
    }

    private void validate(OrderedItem item, Product product) {
        if (product.getAmountInStock() < item.quantity()) {
            throw new NotEnoughStockException("Not enough stock for product " + item.productId());
        }
    }

    private List<Product> getCorrespondingProducts(List<OrderedItem> items) {
        return productRepoPort.getAllByIds(items.stream().map(OrderedItem::productId).toList());
    }

    private Optional<Product> getCorrespondingProduct(OrderedItem item, List<Product> products) {
        return products.stream().filter(p -> p.getId().equals(item.productId())).findFirst();
    }
}