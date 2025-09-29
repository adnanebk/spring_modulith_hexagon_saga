package com.example.demo.common.events;

import com.example.demo.common.models.OrderInput;

public class OrderPayedEvent {
    private OrderInput order;
    public OrderPayedEvent(OrderInput order) {
        this.order = order;
    }
    public OrderInput getOrder() {
        return order;
    }
}
