package com.example.demo.order.ports.out;

import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.common.enums.OrderStatus;

public interface OrderRepoPort {
    Integer create(OrderInput order);

    void update(Integer id, OrderStatus status, Integer paymentId, Integer shippingId);
}
