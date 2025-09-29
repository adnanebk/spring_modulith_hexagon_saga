package com.example.demo.common.events;

import com.example.demo.common.models.OrderInput;

public class OrderShippedEvent  {
    private OrderInput order;

    public OrderShippedEvent(OrderInput order) {
        this.order = order;
    }
    public OrderInput getOrder() {
        return order;
    }


}