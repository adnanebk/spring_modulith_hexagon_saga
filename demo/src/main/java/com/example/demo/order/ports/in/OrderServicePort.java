package com.example.demo.order.ports.in;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.order.domain.OrderStatus;

import java.util.List;

public interface OrderServicePort {


    Integer placeOrder(OrderRequest orderRequest);

    void cancelOrder(Integer orderId, String message);

    void completeOrder(Integer orderId, Integer userId);
}
