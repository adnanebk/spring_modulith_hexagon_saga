package com.example.demo.common.events;

import com.example.demo.common.models.Order;

public class OrderShippedEvent  {
    private Order order;

    public OrderShippedEvent(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }


}