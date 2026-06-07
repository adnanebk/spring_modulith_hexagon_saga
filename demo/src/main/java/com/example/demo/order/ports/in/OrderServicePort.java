package com.example.demo.order.ports.in;

import com.example.demo.order.domain.Order;

import java.util.List;

public interface OrderServicePort {


    Order placeOrder(OrderRequest orderRequest);

    void cancelOrder(Integer orderId, String message);

    void completeOrder(Integer orderId, Integer userId);

    List<Order> getOrderByUserId(Integer userId);
}
