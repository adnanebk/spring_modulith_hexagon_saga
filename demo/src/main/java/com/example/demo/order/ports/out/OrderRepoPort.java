package com.example.demo.order.ports.out;

import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepoPort {
    Integer create(Order order);
    void updateStatus(Integer id, OrderStatus status);

    Optional<String> findCouponCodeById(Integer orderId);

    List<Order> findByUserId(Integer userId);
}
