package com.example.demo.order.ports.out;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.domain.models.OrderInput;

import java.util.UUID;

public interface OrderRepoPort {
    Integer create(OrderInput order);
    void updateStatus(Integer id, OrderStatus status);

    void update(Integer id, OrderStatus orderStatus, UUID locationId, UUID transactionId);
}
