package com.example.demo.common.events;

import com.example.demo.common.events.data.OrderData;

public class OrderProductStockVerifiedEvent {
    private OrderData data;
    public OrderProductStockVerifiedEvent(OrderData data) {
        this.data = data;
    }
    public OrderData getData() {
        return data;
    }
}
