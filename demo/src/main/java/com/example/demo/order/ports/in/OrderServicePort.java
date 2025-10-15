package com.example.demo.order.ports.in;

import com.example.demo.order.domain.models.Order;
import org.springframework.transaction.annotation.Transactional;

public interface OrderServicePort {
    @Transactional
    Integer placeOrder(Order order);
}
