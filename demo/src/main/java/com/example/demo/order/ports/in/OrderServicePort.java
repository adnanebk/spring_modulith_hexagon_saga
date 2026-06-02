package com.example.demo.order.ports.in;

public interface OrderServicePort {


    Integer placeOrder(OrderRequest orderRequest);

    void cancelOrder(Integer orderId, String message);

    void completeOrder(Integer orderId, Integer userId);
}
