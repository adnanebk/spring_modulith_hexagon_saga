package com.example.demo.common.events;

import com.example.demo.common.events.data.OrderData;

public class OrderShippedEvent  {
    private OrderData data;

    public OrderShippedEvent(OrderData data) {
        this.data = data;
    }
    public OrderData getData() {
        return data;
    }


}