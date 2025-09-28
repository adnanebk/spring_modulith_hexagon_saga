package com.example.demo.order.domain.ports.out;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.order.domain.models.OrderOutput;

public interface OrderRepoPort {
    OrderOutput save(OrderInput order);

    void update(Integer id, OrderStatus status, Integer paymentId, Integer shippingId);
}
