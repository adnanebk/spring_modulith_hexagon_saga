package com.example.demo.common.events;

import com.example.demo.common.eventsdata.OrderEventData;

public class OrderShippedEvent  {
    private OrderEventData order;

    public OrderShippedEvent(OrderEventData order) {
        this.order = order;
    }
    public OrderEventData getOrder() {
        return order;
    }


}