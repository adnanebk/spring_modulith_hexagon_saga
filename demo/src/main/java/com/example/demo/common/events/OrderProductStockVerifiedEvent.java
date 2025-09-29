package com.example.demo.common.events;

import com.example.demo.common.models.OrderInput;

public class OrderProductStockVerifiedEvent {
    private OrderInput order;
    public OrderProductStockVerifiedEvent(OrderInput order) {
        this.order = order;
    }
    public OrderInput getOrder() {
        return order;
    }
}
