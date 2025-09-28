package com.example.demo.common.events;

import com.example.demo.common.eventsdata.OrderEventData;

public class OrderPlacedEvent {
    private OrderEventData order;

    public OrderPlacedEvent(OrderEventData order) {
        this.order = order;
    }
    public OrderEventData getOrder() {
        return order;
    }


}
