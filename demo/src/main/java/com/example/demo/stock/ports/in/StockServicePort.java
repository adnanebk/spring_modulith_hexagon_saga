package com.example.demo.stock.ports.in;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.stock.domain.ProductInStock;

import java.util.List;

public interface StockServicePort {
    void updateProductQuantity(OrderDetails orderDetails);

    void rollbackProductQuantity(OrderDetails items, String message);

    void cancelUpdateQuantity(Integer orderId, String message);

    List<ProductInStock> getProducts(List<Integer> productIds);
}
