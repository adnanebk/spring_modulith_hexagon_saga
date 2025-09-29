package com.example.demo.order.domain.ports.out;

import com.example.demo.common.models.OrderInput;
import com.example.demo.order.domain.models.OrderOutput;
import com.example.demo.order.domain.models.OrderStatus;

public interface OrderRepoPort {
    OrderOutput save(OrderInput order);

    void update(Integer id, OrderStatus status, Integer paymentId, Integer shippingId);
}
