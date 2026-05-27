package com.example.demo.order.ports.in;

import com.example.demo.order.domain.OrderItem;
import com.example.demo.order.domain.OrderStatus;

import java.util.List;

public interface OrderServicePort {

    Integer placeOrder(Integer userId, List<OrderItem> orderItems, String paymentToken);

    Integer placeOrder(Integer userId, List<OrderItem> orderItems, String paymentToken, String couponCode);

    void updateStatus(Integer orderId, OrderStatus orderStatus);

    void cancelOrder(Integer orderId, String message);

    void saveCouponUsage(Integer orderId, String couponCode, Integer userId);
}
