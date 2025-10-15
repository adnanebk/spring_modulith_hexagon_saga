package com.example.demo.common.events;

import com.example.demo.common.events.data.OrderData;

public class OrderPayedEvent {
    private OrderData data;
    public OrderPayedEvent(OrderData data) {
        this.data = data;
    }
    public OrderData getData() {
        return data;
    }
}
