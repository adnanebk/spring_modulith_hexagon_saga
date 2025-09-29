package com.example.demo.common.events;

import com.example.demo.common.models.OrderInput;

public class OrderPlacedEvent {
    private OrderInput order;

    public OrderPlacedEvent(OrderInput order) {
        this.order = order;
    }
    public OrderInput getOrder() {
        return order;
    }


}
