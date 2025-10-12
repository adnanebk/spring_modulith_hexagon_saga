package com.example.demo.stock.application;


import com.example.demo.common.models.OrderItem;
import com.example.demo.stock.domain.exeptions.NotEnoughStockException;
import com.example.demo.stock.domain.models.Product;
import com.example.demo.stock.ports.in.ProductRepoPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockEventService {
    private ProductRepoPort productRepoPort;

    public StockEventService(ProductRepoPort productRepoPort) {
        this.productRepoPort = productRepoPort;
    }

    public void updateProductQuantity(List<OrderItem> items) {
        List<Product> products = getCorrespondingProducts(items);
        for (OrderItem item : items) {
            Optional<Product> correspondedProduct = getCorrespondingProduct(item, products);
            correspondedProduct.ifPresent(product -> {
                if (product.getAmountInStock() < item.getQuantity())
                    throw new NotEnoughStockException("Not enough stock");
                product.setAmountInStock(product.getAmountInStock() - item.getQuantity());
            });
        }
        productRepoPort.saveAll(products);
    }


    public void rollbackProductQuantity(List<OrderItem> items) {
        List<Product> products = getCorrespondingProducts(items);
        for (OrderItem item : items) {
            Optional<Product> correspondedProduct = getCorrespondingProduct(item, products);
            correspondedProduct.ifPresent(product -> {
             product.setAmountInStock(product.getAmountInStock() + item.getQuantity());
            });
        }
        productRepoPort.saveAll(products);
    }

    private  Integer getItemQuantity(List<OrderItem> items, Product product) {
        return items.stream().filter(item -> item.getProductId().equals(product.getId()))
                .findFirst()
                .map(OrderItem::getQuantity).orElse(0);
    }
    private List<Product> getCorrespondingProducts(List<OrderItem> items) {
        return productRepoPort.getAllByIds(items.stream().map(OrderItem::getProductId)
                .toList());
    }
    private  Optional<Product> getCorrespondingProduct(OrderItem item, List<Product> products) {
        return products.stream().filter(p -> p.getId().equals(item.getProductId())).findFirst();
    }
}
