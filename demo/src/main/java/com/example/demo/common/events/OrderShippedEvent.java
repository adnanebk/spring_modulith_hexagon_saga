package com.example.demo.common.events;

import com.example.demo.common.events.data.OrderData;

public class OrderShippedEvent  {
    private OrderData order;

    public OrderShippedEvent(OrderData order) {
        this.order = order;
    }
    public OrderData getOrder() {
        return order;
    }


}