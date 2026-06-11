package com.example.demo.order.ports.in;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.order.domain.Order;

import java.util.List;

public interface OrderServicePort {


    Order placeOrder(OrderRequest orderRequest);

    void cancelOrder(Integer orderId, String message);

    void completeOrder(OrderDetails orderDetails);

    List<Order> getOrderByUserId(Integer userId);
}
