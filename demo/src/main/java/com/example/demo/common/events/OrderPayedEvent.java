package com.example.demo.common.events;

import com.example.demo.common.models.Order;

public class OrderPayedEvent {
    private Order order;
    public OrderPayedEvent(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
}
