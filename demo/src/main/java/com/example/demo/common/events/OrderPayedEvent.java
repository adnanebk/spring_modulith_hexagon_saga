package com.example.demo.common.events;

import com.example.demo.common.eventsdata.OrderEventData;

public class OrderPayedEvent {
    private OrderEventData order;
    public OrderPayedEvent(OrderEventData order) {
        this.order = order;
    }
    public OrderEventData getOrder() {
        return order;
    }
}
