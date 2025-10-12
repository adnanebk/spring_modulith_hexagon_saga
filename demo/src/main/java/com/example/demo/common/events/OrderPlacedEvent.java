package com.example.demo.common.events;

import com.example.demo.common.models.Order;

public class OrderPlacedEvent {
    private Order order;

    public OrderPlacedEvent(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }


}
