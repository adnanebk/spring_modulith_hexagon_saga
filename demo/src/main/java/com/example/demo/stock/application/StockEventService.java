package com.example.demo.stock.application;


import com.example.demo.common.eventsdata.OrderItemEventData;
import com.example.demo.stock.domain.exeptions.NotEnoughStockException;
import com.example.demo.stock.domain.models.Product;
import com.example.demo.stock.domain.ports.in.ProductRepoPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockEventService {
    private ProductRepoPort productRepoPort;

    public StockEventService(ProductRepoPort productRepoPort) {
        this.productRepoPort = productRepoPort;
    }

    public void updateStock(List<OrderItemEventData> items) {
        List<Product> products = productRepoPort.getAllByIds(items.stream().map(OrderItemEventData::getProductId).toList());
        for (Product product : products) {
            int itemQuantity = getItemQuantity(items, product);

            if (product.getAmountInStock() < itemQuantity)
                throw new NotEnoughStockException("Not enough stock");
            product.setAmountInStock(product.getAmountInStock() - itemQuantity);

        }
        productRepoPort.saveAll(products);
    }

    public void rollbackStock(List<OrderItemEventData> items) {
        List<Product> products = productRepoPort.getAllByIds(items.stream().map(OrderItemEventData::getProductId).toList());
        for (Product product : products) {
            int itemQuantity = getItemQuantity(items, product);
            product.setAmountInStock(product.getAmountInStock() + itemQuantity);
        }
        productRepoPort.saveAll(products);
    }

    private  Integer getItemQuantity(List<OrderItemEventData> items, Product product) {
        return items.stream().filter(item -> item.getProductId().equals(product.getId()))
                .findFirst()
                .map(OrderItemEventData::getQuantity).orElse(0);
    }
}
