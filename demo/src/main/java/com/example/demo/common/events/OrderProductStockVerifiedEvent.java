package com.example.demo.common.events;

import com.example.demo.common.models.Order;

public class OrderProductStockVerifiedEvent {
    private Order order;
    public OrderProductStockVerifiedEvent(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
}
