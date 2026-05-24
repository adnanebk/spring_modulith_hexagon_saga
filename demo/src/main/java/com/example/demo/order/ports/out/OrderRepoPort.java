package com.example.demo.order.ports.out;

import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;

public interface OrderRepoPort {
    Integer create(Order order);
    void updateStatus(Integer id, OrderStatus status);

}
