package com.example.demo.common.events;

import com.example.demo.common.events.data.OrderData;

public class OrderPlacedEvent {
    private OrderData data;

    public OrderPlacedEvent(OrderData data) {
        this.data = data;
    }
    public OrderData getData() {
        return data;
    }


}
