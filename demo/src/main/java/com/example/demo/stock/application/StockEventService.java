package com.example.demo.stock.application;


import com.example.demo.common.events.data.OrderItemData;
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

    public void updateProductQuantity(List<OrderItemData> items) {
        List<Product> products = getCorrespondingProducts(items);
        for (OrderItemData item : items) {
            Optional<Product> correspondedProduct = getCorrespondingProduct(item, products);
            correspondedProduct.ifPresent(product -> {
                if (product.getAmountInStock() < item.quantity())
                    throw new NotEnoughStockException("Not enough stock");
                product.setAmountInStock(product.getAmountInStock() - item.quantity());
            });
        }
        productRepoPort.saveAll(products);
    }


    public void rollbackProductQuantity(List<OrderItemData> items) {
        List<Product> products = getCorrespondingProducts(items);
        for (OrderItemData item : items) {
            Optional<Product> correspondedProduct = getCorrespondingProduct(item, products);
            correspondedProduct.ifPresent(product -> {
             product.setAmountInStock(product.getAmountInStock() + item.quantity());
            });
        }
        productRepoPort.saveAll(products);
    }

    private List<Product> getCorrespondingProducts(List<OrderItemData> items) {
        return productRepoPort.getAllByIds(items.stream().map(OrderItemData::productId)
                .toList());
    }
    private  Optional<Product> getCorrespondingProduct(OrderItemData item, List<Product> products) {
        return products.stream().filter(p -> p.getId().equals(item.productId())).findFirst();
    }
}
